package com.de190293.mvc;

import com.de190293.mvc.annotation.CustomValidationEngine;
import com.de190293.mvc.annotation.ValidDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Se20A11De190293BmsPeApplicationTests {

    @Test
    void contextLoads() {
    }

    static class TestPastDateDto {
        @ValidDate(pastOnly = true)
        private LocalDate date;

        public TestPastDateDto(LocalDate date) {
            this.date = date;
        }
    }

    @Test
    void testValidDatePastOnly() {
        // Test with past date (should pass)
        TestPastDateDto pastDto = new TestPastDateDto(LocalDate.now().minusDays(5));
        Map<String, String> errors1 = CustomValidationEngine.validate(pastDto);
        assertTrue(errors1.isEmpty(), "Past date should not have errors");

        // Test with today (should pass)
        TestPastDateDto todayDto = new TestPastDateDto(LocalDate.now());
        Map<String, String> errors2 = CustomValidationEngine.validate(todayDto);
        assertTrue(errors2.isEmpty(), "Today's date should not have errors");

        // Test with future date (should fail)
        TestPastDateDto futureDto = new TestPastDateDto(LocalDate.now().plusDays(5));
        Map<String, String> errors3 = CustomValidationEngine.validate(futureDto);
        assertFalse(errors3.isEmpty(), "Future date should have errors");
        assertEquals("Date must be today or in the past", errors3.get("date"));
    }
}
