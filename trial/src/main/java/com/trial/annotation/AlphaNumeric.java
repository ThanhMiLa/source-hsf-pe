package com.trial.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AlphaNumeric {
    String message() default "Only letters and digits are allowed";
}
