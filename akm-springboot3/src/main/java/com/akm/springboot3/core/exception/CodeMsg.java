package com.akm.springboot3.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 系统业务异常响应的状态码配置类
 * 常见的HTTP状态码(HTTP Status Code)： https://www.jianshu.com/p/369db1ba04ea
 *
 * @author xiaojun
 *
 */
@ToString
@Getter
@AllArgsConstructor
public enum CodeMsg {
    /**
     * 1004XX客户端错误
     * 1005XX服务端错误
     */
    UNAUTHORIZED(100401, "未提供token或token已过期"),
    FORBIDDEN(100403, "没有访问权限"),
    NOT_FOUND(100404, "未找到处理方法"),
    METHOD_NOT_ALLOWED(100405, "请求方法不被支持"),
    PRECONDITION_FAILED(100412, "请求参数解密失败"),
    PAYLOAD_TOO_LARGE(100413, "文件过大"),

    SIGN_ERROR(100451, "非法请求"),
    SENSITIVE_WORD(100452, "输入内容中包含敏感词"),
    PUBLIC_KEY_EXPIRED(100453, "秘钥过期"),
    CSRF_TOKEN_ERROR(100454, "CSRF校验不通过"),
    MUST_CHANGE_PASSWORD(100455, "首次登录，请修改密码"),
    PASSWORD_EXPIRED(100456, "密码长时间未修改，请修改密码"),

    INVALID_PARAMETER(100551, "请求参数解析失败"),
    ENCRYPTION_ERROR(100552, "服务端加密发生异常"),
    API_FREQ_LIMIT(100553, "请求过于频繁，请稍后再试"),
    API_NAME_INVALID(100554, "接口命名不符合约定");

    private final Integer code;
    private final String msg;

}
