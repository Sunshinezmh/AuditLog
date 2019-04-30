package com.tfjy.springaop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Class_NAME Log
 * @Auther 张明慧
 * @Date 2019/4/25 17:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Log {
    String name() default "";
}
