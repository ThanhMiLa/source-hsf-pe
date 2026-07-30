package com.de190293.mvc.annotations;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegexPatternTest {

    static class SampleDto {
        @RegexPattern(regexp = "^T\\d{3}$", message = "Must start with T followed by 3 digits")
        private String tourCode;

        public SampleDto(String tourCode) {
            this.tourCode = tourCode;
        }
    }

    @Test
    void testValidRegexPattern() {
        SampleDto dto = new SampleDto("T001");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidRegexPattern() {
        SampleDto dto = new SampleDto("ABC123");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("tourCode"));
        assertEquals("Must start with T followed by 3 digits", errors.get("tourCode"));
    }
}
