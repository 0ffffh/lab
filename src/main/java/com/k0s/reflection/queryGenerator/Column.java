package com.k0s.reflection.queryGenerator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name() default "";
    boolean primaryKey() default false;
    boolean allowNull() default true;
    boolean unique() default false;
    String type() default "VARCHAR(255)";
}

