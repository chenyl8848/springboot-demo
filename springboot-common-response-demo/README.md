# SpringBoot 封装统一返回、全局异常处理、参数校验...

## 前言

SpringBoot 作为现在主流的 JavaEE 开发框架，因其强大的自动装配功能，支持快速迭代一个 Java 应用，深受企业开发者的喜爱！

俗话说，工欲善其事，必先利其器！在使用 SpringBoot 开发时，有必要对一些公共设施搭建好，如统一返回、全局异常处理、参数校验等。这样，才能提升后续的开发效率，避免返工！

## 封装统一返回

在前后端分离的开发模式下，后端开发人员需要写好接口，并提供接口文档，供前端开发进行接口联调。因此，双方需要约定好接口的通用返回格式。

接口返回的通用参数一般需要包含：

- code：响应状态码，一般是 200、0, 用于告诉前端接口请求处理是否成功
- message：响应信息，用于告诉前端接口请求处理结果
- data：响应数据，返回给前端的数据

因此，一个通用的统一返回实体类，可以如下定义：

```java
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
        return build(200, message, data);
    }

    public static <T> Result<T> success(T data) {
        return success("success", data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return build(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return fail(500, message);
    }

}
```

封装好统一实体类后，在控制层接口中，就能进行使用。

```java
@GetMapping("")
public Result<List<String>> query(){
    List<String> list = new ArrayList<>();
    list.add("zhangsan");
    list.add("lisi");
    list.add("wangwu");

    return Result.success("成功", list);
}
```

但是，在 `Result` 类中，存在着硬编码，可以统一封装响应状态码的枚举类，方便后续维护使用。
```java
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
```

定义好响应状态码枚举类后，可以对统一返回实体 `Result` 进行改造。
```java
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
```

## 全局异常处理

##  