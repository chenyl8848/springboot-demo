package com.codechen.common.vo;

import com.codechen.common.enums.ResultCodeEnum;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date：2024-10-12 16:37
 * @description：通用返回实体类
 */
// 使用 lombok 简化 @Data 注解 getter/setter
@Data
public class Result<T> {

    /** 响应码 */
    private Integer code;

    /** 响应消息 */
    private String message;

    /** 响应数据 */
    private T data;

    /** 构造函数私有化，避免在使用中通过 new 构造对象，统一通过静态方法构造 */
    private Result() {

    }

    public static <T> Result<T> build(Integer code, String message, T data) {
        Result<T> result = new Result<>();

        result.setCode(code);
        result.setMessage(message);
        result.setData(data);

        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        return build(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> success(T data) {
        return success(ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return build(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return fail(ResultCodeEnum.FAIL.getCode(), message);
    }

    public static <T> Result<T> build(ResultCodeEnum resultCodeEnum, T data) {
        return build(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), data);
    }

}
