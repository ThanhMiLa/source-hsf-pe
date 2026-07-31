package com.de190293.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidDate {
    boolean futureOnly() default false;
    boolean pastOnly() default false;
    long maxDaysFromToday() default -1;
    long maxDaysInPast() default -1;
    String message() default "";
}
