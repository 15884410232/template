package com.sp.config.hibernate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 次注解是依赖于hibenate的ddl-auto属性，因为ddl-auto的机制，所以在表结构不发生变更的前提得下，是无法通过此注解修改表批注的
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Comment {
    String value() default "";
}