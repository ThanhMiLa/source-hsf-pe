package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IntRange {
    long min() default Long.MIN_VALUE;
    long max() default Long.MAX_VALUE;
    String message() default "Giá trị số nằm ngoài khoảng cho phép";
}
