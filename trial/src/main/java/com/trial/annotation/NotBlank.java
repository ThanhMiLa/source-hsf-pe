package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotBlank {
    String message() default "Trường này không được để trống";
}
