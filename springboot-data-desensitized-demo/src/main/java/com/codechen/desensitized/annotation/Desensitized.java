package com.codechen.desensitized.annotation;

import com.codechen.desensitized.constant.DesensitizedTypeEnum;
import com.codechen.desensitized.serializer.DesensitizedSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：Java陈序员
 * @date 2025/10/26 15:08
 * @description
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// 用于指示在序列化和反序列化JSON时，只处理当前类的注解，而不处理继承自父类的注解
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedSerializer.class)
public @interface Desensitized {

    /**
     * 敏感信息类型
     */
    DesensitizedTypeEnum type() default DesensitizedTypeEnum.CUSTOMER;

    /**
     * 前置不需要打码的长度
     */
    int prefix() default 0;

    /**
     * 后置不需要打码的长度
     */
    int suffix() default 0;

    /**
     * 打码字符
     */
    String maskChar() default "*";
}
