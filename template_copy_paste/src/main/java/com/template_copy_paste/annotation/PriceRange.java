package com.template_copy_paste.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PriceRange {
    double min() default 0.0;
    double max() default Double.MAX_VALUE;
    String message() default "Giá tiền không hợp lệ";
}
