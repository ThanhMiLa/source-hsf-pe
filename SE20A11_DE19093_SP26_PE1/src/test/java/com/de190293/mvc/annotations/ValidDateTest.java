package com.de190293.mvc.annotations;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidDateTest {

    static class SampleDto {
        @ValidDate(futureOnly = true, maxDaysFromToday = 30)
        private LocalDate startDate;

        public SampleDto(LocalDate startDate) {
            this.startDate = startDate;
        }
    }

    @Test
    void testValidFutureDateWithinRange() {
        SampleDto dto = new SampleDto(LocalDate.now().plusDays(10));
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testPastDateFailsWhenFutureOnly() {
        SampleDto dto = new SampleDto(LocalDate.now().minusDays(1));
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("startDate"));
        assertEquals("Date must be today or in the future", errors.get("startDate"));
    }

    @Test
    void testDateExceedingMaxDaysFails() {
        SampleDto dto = new SampleDto(LocalDate.now().plusDays(31));
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("startDate"));
        assertEquals("Date cannot exceed 30 days from today", errors.get("startDate"));
    }

    static class PastDto {
        @ValidDate(pastOnly = true, maxDaysInPast = 15)
        private LocalDate createdDate;

        public PastDto(LocalDate createdDate) {
            this.createdDate = createdDate;
        }
    }

    @Test
    void testMaxDaysInPast() {
        PastDto validDto = new PastDto(LocalDate.now().minusDays(10));
        assertTrue(CustomValidationEngine.validate(validDto).isEmpty());

        PastDto invalidDto = new PastDto(LocalDate.now().minusDays(20));
        Map<String, String> errors = CustomValidationEngine.validate(invalidDto);
        assertTrue(errors.containsKey("createdDate"));
        assertEquals("Date cannot exceed 15 days in the past", errors.get("createdDate"));
    }
}
