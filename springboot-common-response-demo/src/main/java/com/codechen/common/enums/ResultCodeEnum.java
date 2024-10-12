package com.codechen.common.enums;

import lombok.Getter;

/**
 * @author：Java陈序员
 * @date：2024-10-12 17:02
 * @description：通用返回码枚举类
 */
// 使用 Lombok 的 @Getter 注解省略 getter
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    ;

    /** 响应码 */
    private final Integer code;

    /** 响应信息 */
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
