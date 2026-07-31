package com.de190293.mvc.annotations;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidAgeTest {

    static class UserDto {
        @ValidAge(min = 18, max = 60)
        private String birthDate;

        public UserDto(String birthDate) {
            this.birthDate = birthDate;
        }
    }

    static class MinOnlyDto {
        @ValidAge(min = 18)
        private LocalDate birthDate;

        public MinOnlyDto(LocalDate birthDate) {
            this.birthDate = birthDate;
        }
    }

    @Test
    void testValidAgeRange() {
        UserDto validUser = new UserDto(LocalDate.now().minusYears(25).toString());
        Map<String, String> errors = CustomValidationEngine.validate(validUser);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testUnderAgeFails() {
        UserDto underAgeUser = new UserDto(LocalDate.now().minusYears(15).toString());
        Map<String, String> errors = CustomValidationEngine.validate(underAgeUser);
        assertTrue(errors.containsKey("birthDate"));
        assertEquals("Age must be between 18 and 60 years old", errors.get("birthDate"));
    }

    @Test
    void testOverAgeFails() {
        UserDto overAgeUser = new UserDto(LocalDate.now().minusYears(65).toString());
        Map<String, String> errors = CustomValidationEngine.validate(overAgeUser);
        assertTrue(errors.containsKey("birthDate"));
        assertEquals("Age must be between 18 and 60 years old", errors.get("birthDate"));
    }

    @Test
    void testMinAgeOnly() {
        MinOnlyDto validDto = new MinOnlyDto(LocalDate.now().minusYears(70));
        assertTrue(CustomValidationEngine.validate(validDto).isEmpty());

        MinOnlyDto invalidDto = new MinOnlyDto(LocalDate.now().minusYears(16));
        Map<String, String> errors = CustomValidationEngine.validate(invalidDto);
        assertTrue(errors.containsKey("birthDate"));
        assertEquals("Age must be at least 18 years old", errors.get("birthDate"));
    }

    @Test
    void testInvalidDateFormat() {
        UserDto invalidFormatUser = new UserDto("2020-02-31");
        Map<String, String> errors = CustomValidationEngine.validate(invalidFormatUser);
        assertTrue(errors.containsKey("birthDate"));
        assertEquals("Invalid date format (expected YYYY-MM-DD)", errors.get("birthDate"));
    }
}
