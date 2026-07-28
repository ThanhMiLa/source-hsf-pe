package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PriceRange {
    double min() default 0.0;
    double max() default Double.MAX_VALUE;
    String message() default "Giá tiền không hợp lệ";
}
