package com.de190293.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidDate {
    boolean futureOnly() default false;
    long maxDaysFromToday() default -1;
    String message() default "";
}
