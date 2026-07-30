package com.de190293.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VNPhone {
    String message() default "Số điện thoại phải bao gồm 10 chữ số và bắt đầu bằng số 0";
}
