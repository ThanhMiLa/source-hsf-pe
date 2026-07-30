package com.de190293.mvc.annotations;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidStatusTest {

    static class SampleDto {
        @ValidStatus(allowed = {"ACTIVE", "INACTIVE", "PENDING"}, caseSensitive = false)
        private String status;

        public SampleDto(String status) {
            this.status = status;
        }
    }

    static class CaseSensitiveDto {
        @ValidStatus(allowed = {"ACTIVE", "INACTIVE"}, caseSensitive = true)
        private String status;

        public CaseSensitiveDto(String status) {
            this.status = status;
        }
    }

    @Test
    void testValidStatusIgnoreCase() {
        SampleDto dto = new SampleDto("active");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidStatus() {
        SampleDto dto = new SampleDto("DELETED");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("status"));
        assertEquals("Invalid status. Allowed values are: ACTIVE, INACTIVE, PENDING", errors.get("status"));
    }

    @Test
    void testCaseSensitiveFail() {
        CaseSensitiveDto dto = new CaseSensitiveDto("active");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("status"));
    }

    @Test
    void testCaseSensitiveSuccess() {
        CaseSensitiveDto dto = new CaseSensitiveDto("ACTIVE");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.isEmpty());
    }
}
