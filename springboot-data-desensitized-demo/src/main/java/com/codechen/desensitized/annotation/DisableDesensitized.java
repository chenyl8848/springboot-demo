package com.codechen.desensitized.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：Java陈序员
 * @date 2025/10/26 16:34
 * @description 是否开启数据脱敏注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableDesensitized {

    boolean value() default true;
}
