package com.codechen.desensitized.util;

import java.lang.reflect.Field;

/**
 * @author：Java陈序员
 * @date 2025/10/26 17:28
 * @description 递归反射工具类
 */
public class RecursiveReflectUtil {
      public static Class<?> getClass(Class<?> c, String fieldName) {
        if (c !=null && !hasField(c, fieldName)) {
            return getClass(c.getSuperclass(), fieldName);
        }
        return c;
    }

    public static boolean hasField(Class<?> c, String fieldName){
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }
}
