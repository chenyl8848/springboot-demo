package com.codechen.desensitized.serializer;

import com.codechen.desensitized.annotation.Desensitized;
import com.codechen.desensitized.constant.DesensitizedTypeEnum;
import com.codechen.desensitized.util.DesensitizedUtil;
import com.codechen.desensitized.util.RecursiveReflectUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author：Java陈序员
 * @date 2025/10/26 15:18
 * @description 脱敏序列化类
 */
public class DesensitizedSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private DesensitizedTypeEnum type;

    private int prefix;

    private int suffix;

    private String maskChar;

    public DesensitizedSerializer(DesensitizedTypeEnum type, int prefix, int suffix, String maskChar) {
        this.type = type;
        this.prefix = prefix;
        this.suffix = suffix;
        this.maskChar = maskChar;
    }

    public DesensitizedSerializer() {
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        boolean flag = true;
        Object currentValue = jsonGenerator.getOutputContext().getCurrentValue();
        Class<?> clazz = currentValue.getClass();
        Class<?> aClass = RecursiveReflectUtil.getClass(clazz, "desensitized");
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);

            String name = field.getName();
            Class<?> type = field.getType();
            if (type.isAssignableFrom(Boolean.class) && name.equals("desensitized")) {
                try {
                    flag = (boolean) field.get(currentValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (flag) {
            if (StringUtils.isNoneBlank(s) && type != null) {
                switch (type) {
                    case CHINESE_NAME:
                        jsonGenerator.writeString(DesensitizedUtil.chineseName(s));
                        break;
                    case MOBILE:
                        jsonGenerator.writeString(DesensitizedUtil.mobilePhone(s));
                        break;
                    case ID_CARD:
                        jsonGenerator.writeString(DesensitizedUtil.idCard(s));
                        break;
                    case BANK_CARD:
                        jsonGenerator.writeString(DesensitizedUtil.bankCard(s));
                        break;
                    case CUSTOMER:
                        jsonGenerator.writeString(DesensitizedUtil.densenValue(s, prefix, suffix, maskChar));
                        break;
                    default:
                        throw new IllegalArgumentException("未知的数据脱敏类型 " + type);
                }
            } else {
                jsonGenerator.writeString(s);
            }
        } else {
            jsonGenerator.writeString(s);
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {

        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Desensitized annotation = beanProperty.getAnnotation(Desensitized.class);

                if (annotation != null) {
                    return new DesensitizedSerializer(annotation.type(), annotation.prefix(), annotation.suffix(), annotation.maskChar());
                } else {
                    annotation = beanProperty.getContextAnnotation(Desensitized.class);
                }
            }

            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }

        return serializerProvider.findNullValueSerializer(null);
    }
}
