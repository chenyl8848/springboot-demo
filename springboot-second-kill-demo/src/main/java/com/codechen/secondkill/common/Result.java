package com.codechen.secondkill.common;

import lombok.Data;

/**
 * @author cyl
 * @date 2023-02-11 11:36
 * @description 通用处理类
 */
@Data
public class Result {

    /**
     * 统一响应码
     */
    private Integer code;
    /**
     * 统一响应信息
     */
    private String message;
    /**
     * 统一响应数据
     */
    private Object data;

    public static Result success(String message) {
        return success(message, null);
    }

    public static Result success(String message, Object data) {
        return common(200, message, data);
    }

    public static Result fail(String message) {
        return fail(message, null);
    }

    public static Result fail(String message, Object data) {
        return common(500, message, data);
    }

    public static Result common(Integer code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


}
