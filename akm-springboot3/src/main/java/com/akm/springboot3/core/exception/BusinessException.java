package com.akm.springboot3.core.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 自定义业务逻辑异常类
 *
 * @author xiaojun
 *
 */
@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2688655198558126042L;
    private Integer code;

    public BusinessException() {
    }

    public BusinessException(Integer code) {
        super();
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
    }

    @Override
    public String toString() {
        return "BusinessException{" +
            "code=" + code +
            ", msg=" + super.getMessage() +
            '}';
    }
}
