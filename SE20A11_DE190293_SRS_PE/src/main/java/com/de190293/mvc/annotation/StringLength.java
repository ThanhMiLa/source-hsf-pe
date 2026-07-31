package com.de190293.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringLength {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
    String message() default "Độ dài chuỗi không hợp lệ";
}
