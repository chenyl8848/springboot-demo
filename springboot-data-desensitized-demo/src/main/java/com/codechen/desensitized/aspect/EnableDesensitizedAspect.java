package com.codechen.desensitized.aspect;

import com.codechen.desensitized.annotation.Desensitized;
import com.codechen.desensitized.annotation.DisableDesensitized;
import com.codechen.desensitized.util.RecursiveReflectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author：Java陈序员
 * @date 2025/10/26 16:36
 * @description 是否开启数据脱敏切面
 */
@Aspect
@Component
public class EnableDesensitizedAspect {

    @Pointcut("@annotation(com.codechen.desensitized.annotation.DisableDesensitized)")
    public void pointCut() {

    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) throws IllegalAccessException {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        DisableDesensitized annotation = method.getDeclaredAnnotation(DisableDesensitized.class);
        if (annotation != null && annotation.value()) {
            Class<?> aClass = RecursiveReflectUtil.getClass(result.getClass(), "desensitized");
            if (aClass != null) {
                for (Field field : aClass.getDeclaredFields()) {
                    field.setAccessible(true);

                    String name = field.getName();
                    Class<?> type = field.getType();
                    if (type.isAssignableFrom(Boolean.class) && name.equals("desensitized")) {
                        field.set(result, false);
                    }
                }
            }
        }
    }
}
