package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringLength {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
    String message() default "Độ dài chuỗi không hợp lệ";
}
