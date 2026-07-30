package com.de190293.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidStatus {
    /**
     * Danh sách các giá trị hợp lệ được phép.
     */
    String[] allowed() default {};

    /**
     * Phân biệt chữ hoa/chữ thường (mặc định: false - không phân biệt hoa thường).
     */
    boolean caseSensitive() default false;

    /**
     * Thông báo lỗi tùy chỉnh.
     */
    String message() default "";
}
