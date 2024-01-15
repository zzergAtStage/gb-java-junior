package com.zergatstage.seminar02.task02;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    String message() default "Field value is out of range";
}

