package com.de190293.mvc.annotations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomValidationEngineTest {

    // Dummy DTO containing all annotations for testing
    static class FullDto {
        @NotBlank(message = "Name cannot be blank")
        private String name;

        @AlphaNumeric(message = "Username must be alphanumeric")
        private String username;

        @StringLength(min = 3, max = 10, message = "Code length must be 3 to 10")
        private String code;

        @PriceRange(min = 10.0, max = 1000.0, message = "Price must be > 10 and < 1000")
        private Double price;

        @IntRange(min = 1, max = 100, message = "Quantity must be between 1 and 100")
        private Integer quantity;

        @VNPhone(message = "Invalid VN Phone")
        private String phone;

        @ValidEmail(message = "Invalid Email")
        private String email;

        @ValidDate(futureOnly = true, maxDaysFromToday = 30)
        private LocalDate startDate;

        @ValidStatus(allowed = {"ACTIVE", "INACTIVE"}, caseSensitive = false)
        private String status;

        @RegexPattern(regexp = "^T\\d{3}$", message = "Tour code must start with T followed by 3 digits")
        private String tourCode;

        public FullDto(String name, String username, String code, Double price, Integer quantity,
                       String phone, String email, LocalDate startDate, String status, String tourCode) {
            this.name = name;
            this.username = username;
            this.code = code;
            this.price = price;
            this.quantity = quantity;
            this.phone = phone;
            this.email = email;
            this.startDate = startDate;
            this.status = status;
            this.tourCode = tourCode;
        }
    }

    @Test
    @DisplayName("Test all valid fields -> No errors")
    void testAllValidFields() {
        FullDto dto = new FullDto(
                "John Doe",
                "john123",
                "CODE12",
                150.0,
                10,
                "0912345678",
                "john@example.com",
                LocalDate.now().plusDays(5),
                "active",
                "T123"
        );

        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.isEmpty(), "Expected no errors but got: " + errors);
    }

    @Test
    @DisplayName("Test @NotBlank validation")
    void testNotBlank() {
        FullDto nullDto = new FullDto(null, "user1", "COD", 20.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors1 = CustomValidationEngine.validate(nullDto);
        assertTrue(errors1.containsKey("name"));
        assertEquals("Name cannot be blank", errors1.get("name"));

        FullDto emptyDto = new FullDto("   ", "user1", "COD", 20.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors2 = CustomValidationEngine.validate(emptyDto);
        assertTrue(errors2.containsKey("name"));
    }

    @Test
    @DisplayName("Test @AlphaNumeric validation")
    void testAlphaNumeric() {
        FullDto invalidDto = new FullDto("John", "user_123!", "COD", 20.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors = CustomValidationEngine.validate(invalidDto);
        assertTrue(errors.containsKey("username"));
        assertEquals("Username must be alphanumeric", errors.get("username"));
    }

    @Test
    @DisplayName("Test @StringLength min/max & spaces check")
    void testStringLength() {
        // Short length
        FullDto shortDto = new FullDto("John", "user1", "AB", 20.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors1 = CustomValidationEngine.validate(shortDto);
        assertTrue(errors1.containsKey("code"));

        // Long length
        FullDto longDto = new FullDto("John", "user1", "VERYLONGCODE123", 20.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors2 = CustomValidationEngine.validate(longDto);
        assertTrue(errors2.containsKey("code"));

        // Spaces at ends
        FullDto spaceDto = new FullDto("John", "user1", " CODE ", 20.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors3 = CustomValidationEngine.validate(spaceDto);
        assertTrue(errors3.containsKey("code"));
        assertEquals("Must not contain spaces at both sides", errors3.get("code"));
    }

    @Test
    @DisplayName("Test @PriceRange min/max validation")
    void testPriceRange() {
        // Too low
        FullDto lowDto = new FullDto("John", "user1", "COD", 5.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors1 = CustomValidationEngine.validate(lowDto);
        assertTrue(errors1.containsKey("price"));

        // Too high
        FullDto highDto = new FullDto("John", "user1", "COD", 1500.0, 5, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors2 = CustomValidationEngine.validate(highDto);
        assertTrue(errors2.containsKey("price"));
    }

    @Test
    @DisplayName("Test @IntRange min/max validation")
    void testIntRange() {
        // Less than min
        FullDto minDto = new FullDto("John", "user1", "COD", 50.0, 0, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors1 = CustomValidationEngine.validate(minDto);
        assertTrue(errors1.containsKey("quantity"));

        // Greater than max
        FullDto maxDto = new FullDto("John", "user1", "COD", 50.0, 101, "0912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors2 = CustomValidationEngine.validate(maxDto);
        assertTrue(errors2.containsKey("quantity"));
    }

    static class StringNumberDto {
        @IntRange(min = 1, max = 100)
        private String quantity;

        @PriceRange(min = 10.0, max = 1000.0)
        private String price;

        public StringNumberDto(String quantity, String price) {
            this.quantity = quantity;
            this.price = price;
        }
    }

    @Test
    @DisplayName("Test non-numeric String passed to @IntRange and @PriceRange")
    void testNonNumericStringInput() {
        StringNumberDto dto = new StringNumberDto("abc", "xyz");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("quantity"));
        assertEquals("Must be a valid integer number", errors.get("quantity"));
        assertTrue(errors.containsKey("price"));
        assertEquals("Must be a valid number", errors.get("price"));
    }

    @Test
    @DisplayName("Test @VNPhone format validation")
    void testVNPhone() {
        // Not starting with 0
        FullDto dto1 = new FullDto("John", "user1", "COD", 50.0, 10, "1912345678", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors1 = CustomValidationEngine.validate(dto1);
        assertTrue(errors1.containsKey("phone"));

        // Not 10 digits
        FullDto dto2 = new FullDto("John", "user1", "COD", 50.0, 10, "091234567", "a@b.com", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors2 = CustomValidationEngine.validate(dto2);
        assertTrue(errors2.containsKey("phone"));
    }

    @Test
    @DisplayName("Test @ValidEmail format validation")
    void testValidEmail() {
        FullDto dto = new FullDto("John", "user1", "COD", 50.0, 10, "0912345678", "invalid-email", LocalDate.now(), "ACTIVE", "T001");
        Map<String, String> errors = CustomValidationEngine.validate(dto);
        assertTrue(errors.containsKey("email"));
        assertEquals("Invalid Email", errors.get("email"));
    }

    @Test
    @DisplayName("Test null object input -> returns empty map")
    void testNullObject() {
        Map<String, String> errors = CustomValidationEngine.validate(null);
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }
}
