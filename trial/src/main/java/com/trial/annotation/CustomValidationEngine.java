package com.trial.annotation;

import java.lang.reflect.Field;
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
                if (field.isAnnotationPresent(PriceRange.class) && value instanceof Number num) {
                    PriceRange range = field.getAnnotation(PriceRange.class);
                    double val = num.doubleValue();

                    if (val <= range.min() || val >= range.max()) {
                        String msg = range.message().isEmpty()
                                ? "Value must be greater than " + range.min() + " and less than " + range.max()
                                : range.message();
                        errors.put(fieldName, msg);
                        continue;
                    }
                }

                // =========================================================================
                // 5. CHECK INT RANGE (Số nguyên)
                // =========================================================================
                if (field.isAnnotationPresent(IntRange.class) && value instanceof Number num) {
                    IntRange range = field.getAnnotation(IntRange.class);
                    long val = num.longValue();

                    if (val < range.min() || val > range.max()) {
                        String msg = range.message().isEmpty()
                                ? "Value must be between " + range.min() + " and " + range.max()
                                : range.message();
                        errors.put(fieldName, msg);
                        continue;
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

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return errors;
    }
}