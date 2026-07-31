package com.de190293.mvc.annotation;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class CustomValidationEngine {

    public static Map<String, String> validate(Object object) {
        Map<String, String> errors = new HashMap<>();
        if (object == null) return errors;

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Cho phép đọc private fields
            try {
                Object value = field.get(object);
                String fieldName = field.getName();

                // =========================================================================
                // 1. CHECK NOT BLANK (BẮT BỘC CHẠY ĐẦU TIÊN)
                // =========================================================================
                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || value.toString().trim().isEmpty()) {
                        NotBlank notBlank = field.getAnnotation(NotBlank.class);
                        String msg = notBlank.message().isEmpty() ? "Field is required" : notBlank.message();
                        errors.put(fieldName, msg);
                        continue; // Để trống thì báo lỗi luôn, skip các rule bên dưới
                    }
                }

                // Nếu value null thì bỏ qua các check logic bên dưới để tránh NullPointerException
                if (value == null) continue;

                // =========================================================================
                // 2. CHECK ALPHANUMERIC (Chỉ chứa chữ a-Z và số 0-9)
                // =========================================================================
                if (field.isAnnotationPresent(AlphaNumeric.class) && value instanceof String str) {
                    if (!str.isEmpty() && !str.matches("^[a-zA-Z0-9]+$")) {
                        AlphaNumeric anno = field.getAnnotation(AlphaNumeric.class);
                        String msg = anno.message().isEmpty() ? "Only letters and digits are allowed" : anno.message();
                        errors.put(fieldName, msg);
                        continue;
                    }
                }

                // =========================================================================
                // 3. CHECK STRING LENGTH (Check độ dài min/max & Space 2 đầu)
                // =========================================================================
                if (field.isAnnotationPresent(StringLength.class) && value instanceof String str) {
                    StringLength length = field.getAnnotation(StringLength.class);

                    // Check min & max length
                    if (str.length() < length.min() || str.length() > length.max()) {
                        String msg = length.message().isEmpty()
                                ? "Length must be between " + length.min() + " and " + length.max() + " characters"
                                : length.message();
                        errors.put(fieldName, msg);
                        continue;
                    }

                    // Check space 2 đầu (No space at both sides)
                    if (!str.isEmpty() && !str.equals(str.trim())) {
                        errors.put(fieldName, "Must not contain spaces at both sides");
                        continue;
                    }
                }

                // =========================================================================
                // 4. CHECK PRICE RANGE (Số thực / Giá tiền)
                // =========================================================================
                if (field.isAnnotationPresent(PriceRange.class)) {
                    PriceRange range = field.getAnnotation(PriceRange.class);
                    Double val = null;

                    if (value instanceof Number num) {
                        val = num.doubleValue();
                    } else if (value instanceof String str && !str.trim().isEmpty()) {
                        try {
                            val = Double.parseDouble(str.trim());
                        } catch (NumberFormatException e) {
                            errors.put(fieldName, "Must be a valid number");
                            continue;
                        }
                    }

                    if (val != null) {
                        if (val <= range.min() || val >= range.max()) {
                            String msg = range.message().isEmpty()
                                    ? "Value must be greater than " + range.min() + " and less than " + range.max()
                                    : range.message();
                            errors.put(fieldName, msg);
                            continue;
                        }
                    }
                }

                // =========================================================================
                // 5. CHECK INT RANGE (Số nguyên)
                // =========================================================================
                if (field.isAnnotationPresent(IntRange.class)) {
                    IntRange range = field.getAnnotation(IntRange.class);
                    Long val = null;

                    if (value instanceof Number num) {
                        val = num.longValue();
                    } else if (value instanceof String str && !str.trim().isEmpty()) {
                        try {
                            val = Long.parseLong(str.trim());
                        } catch (NumberFormatException e) {
                            errors.put(fieldName, "Must be a valid integer number");
                            continue;
                        }
                    }

                    if (val != null) {
                        if (val < range.min() || val > range.max()) {
                            String msg = range.message().isEmpty()
                                    ? "Value must be between " + range.min() + " and " + range.max()
                                    : range.message();
                            errors.put(fieldName, msg);
                            continue;
                        }
                    }
                }

                // =========================================================================
                // 6. CHECK VN PHONE (10 số, bắt đầu bằng 0)
                // =========================================================================
                if (field.isAnnotationPresent(VNPhone.class) && value instanceof String str) {
                    if (!str.isEmpty() && !str.matches("^0\\d{9}$")) {
                        VNPhone anno = field.getAnnotation(VNPhone.class);
                        String msg = anno.message().isEmpty() ? "Invalid phone format" : anno.message();
                        errors.put(fieldName, msg);
                        continue;
                    }
                }

                // =========================================================================
                // 7. CHECK VALID EMAIL
                // =========================================================================
                if (field.isAnnotationPresent(ValidEmail.class) && value instanceof String str) {
                    String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
                    if (!str.isEmpty() && !str.matches(emailRegex)) {
                        ValidEmail anno = field.getAnnotation(ValidEmail.class);
                        String msg = anno.message().isEmpty() ? "Invalid email format" : anno.message();
                        errors.put(fieldName, msg);
                        continue;
                    }
                }

                // =========================================================================
                // 8. CHECK VALID DATE (LocalDate hoặc String đại diện cho ngày)
                // =========================================================================
                if (field.isAnnotationPresent(ValidDate.class)) {
                    ValidDate anno = field.getAnnotation(ValidDate.class);
                    LocalDate dateVal = null;

                    if (value instanceof LocalDate ld) {
                        dateVal = ld;
                    } else if (value instanceof String str && !str.trim().isEmpty()) {
                        try {
                            dateVal = LocalDate.parse(str.trim());
                        } catch (DateTimeParseException e) {
                            String msg = anno.message().isEmpty() ? "Invalid date format (expected YYYY-MM-DD)" : anno.message();
                            errors.put(fieldName, msg);
                            continue;
                        }
                    }

                    if (dateVal != null) {
                        LocalDate today = LocalDate.now();

                        // Check nếu bắt buộc phải là ngày tương lai (>= today)
                        if (anno.futureOnly() && dateVal.isBefore(today)) {
                            String msg = anno.message().isEmpty()
                                    ? "Date must be today or in the future"
                                    : anno.message();
                            errors.put(fieldName, msg);
                            continue;
                        }

                        // Check nếu bắt buộc phải là ngày quá khứ (<= today)
                        if (anno.pastOnly() && dateVal.isAfter(today)) {
                            String msg = anno.message().isEmpty()
                                    ? "Date must be today or in the past"
                                    : anno.message();
                            errors.put(fieldName, msg);
                            continue;
                        }

                        // Check khoảng range tối đa (+ N ngày tính từ ngày hiện tại)
                        if (anno.maxDaysFromToday() >= 0) {
                            LocalDate maxDate = today.plusDays(anno.maxDaysFromToday());
                            if (dateVal.isAfter(maxDate)) {
                                String msg = anno.message().isEmpty()
                                        ? "Date cannot exceed " + anno.maxDaysFromToday() + " days from today"
                                        : anno.message();
                                errors.put(fieldName, msg);
                                continue;
                            }
                        }
                    }
                }

                // =========================================================================
                // 9. CHECK VALID STATUS / ALLOWED VALUES
                // =========================================================================
                if (field.isAnnotationPresent(ValidStatus.class) && value != null) {
                    ValidStatus anno = field.getAnnotation(ValidStatus.class);
                    String strVal = value.toString().trim();

                    if (!strVal.isEmpty() && anno.allowed().length > 0) {
                        boolean isValid = false;
                        for (String allowed : anno.allowed()) {
                            if (anno.caseSensitive()) {
                                if (allowed.equals(strVal)) {
                                    isValid = true;
                                    break;
                                }
                            } else {
                                if (allowed.equalsIgnoreCase(strVal)) {
                                    isValid = true;
                                    break;
                                }
                            }
                        }

                        if (!isValid) {
                            String msg = anno.message().isEmpty()
                                    ? "Invalid status. Allowed values are: " + String.join(", ", anno.allowed())
                                    : anno.message();
                            errors.put(fieldName, msg);
                            continue;
                        }
                    }
                }

                // =========================================================================
                // 10. CHECK REGEX PATTERN
                // =========================================================================
                if (field.isAnnotationPresent(RegexPattern.class) && value != null) {
                    RegexPattern anno = field.getAnnotation(RegexPattern.class);
                    String strVal = value.toString().trim();

                    if (!strVal.isEmpty() && !anno.regexp().isEmpty()) {
                        if (!strVal.matches(anno.regexp())) {
                            String msg = anno.message().isEmpty()
                                    ? "Value does not match required pattern"
                                    : anno.message();
                            errors.put(fieldName, msg);
                            continue;
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return errors;
    }
}