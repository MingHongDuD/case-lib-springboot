package com.damon.log;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解：标记需要记录日志的方法
 *
 * @author damon du/minghongdud
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Loggable {

    /**
     * 指定日志级别，如果不指定则使用 INFO 级别。
     */
    LogLevel value() default LogLevel.INFO;

    /**
     * 控制是否记录方法参数。
     */
    boolean params() default true;

    /**
     * 控制是否记录方法的返回值。
     */
    boolean result() default true;

    /**
     * 指定需要记录的参数对象的特定字段名。
     */
    String[] specifParams() default {};
}
