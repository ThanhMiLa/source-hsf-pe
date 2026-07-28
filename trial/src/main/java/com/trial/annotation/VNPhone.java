package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VNPhone {
    String message() default "Số điện thoại phải bao gồm 10 chữ số và bắt đầu bằng số 0";
}
