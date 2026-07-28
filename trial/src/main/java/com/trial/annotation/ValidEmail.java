package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidEmail {
    String message() default "Email không đúng định dạng chuẩn";
}
