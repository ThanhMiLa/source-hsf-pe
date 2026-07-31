package com.trial.util;

import java.lang.reflect.Field;

public class GenericMapper {

    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) return null;

        try {
            // Khởi tạo instance mới của DTO
            T target = targetClass.getDeclaredConstructor().newInstance();

            // Lấy tất cả field của DTO và Entity
            Field[] targetFields = targetClass.getDeclaredFields();
            Class<?> sourceClass = source.getClass();

            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                try {
                    // Tìm field có cùng tên bên Entity
                    Field sourceField = sourceClass.getDeclaredField(targetField.getName());
                    sourceField.setAccessible(true);

                    // Nếu cùng kiểu dữ liệu thì copy giá trị sang
                    if (targetField.getType().equals(sourceField.getType())) {
                        Object value = sourceField.get(source);
                        targetField.set(target, value);
                    }
                } catch (NoSuchFieldException ignored) {
                    // Nếu field bên DTO không có bên Entity (vd: bookType - dạng String) thì bỏ qua
                }
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping object", e);
        }
    }
}
