package com.de190293.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IntRange {
    long min() default Long.MIN_VALUE;
    long max() default Long.MAX_VALUE;
    String message() default "Giá trị số nằm ngoài khoảng cho phép";
}
