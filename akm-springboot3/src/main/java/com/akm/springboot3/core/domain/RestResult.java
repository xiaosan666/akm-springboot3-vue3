package com.akm.springboot3.core.domain;

import com.akm.springboot3.core.exception.CodeMsg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Schema(title = "返回对象")
@Getter
@Setter
public class RestResult<T> {

    public static final Integer SUCCESS = 100200;
    public static final Integer FAIL = 100500;

    @Schema(title = "状态码，非100200则失败")
    private Integer code = SUCCESS;

    @Schema(title = "响应信息")
    private String msg = "";

    @Schema(title = "响应的数据")
    private T data;

    public static <E> RestResult<E> success() {
        RestResult<E> bean = new RestResult<>();
        bean.code = SUCCESS;
        bean.msg = "成功";
        return bean;
    }

    public static <E> RestResult<E> success(E result) {
        return success("", result);
    }

    public static <E> RestResult<E> success(String msg, E result) {
        RestResult<E> bean = new RestResult<>();
        bean.code = SUCCESS;
        bean.msg = msg;
        bean.data = result;
        return bean;
    }

    public static <E> RestResult<E> fail(String msg) {
        return fail(FAIL, msg, null);
    }

    public static <E> RestResult<E> fail(E data) {
        return fail(FAIL, "", data);
    }

    public static <E> RestResult<E> fail(CodeMsg codeMsg) {
        return fail(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    public static <E> RestResult<E> fail(Integer code, String msg) {
        return fail(code, msg, null);
    }

    public static <E> RestResult<E> fail(String msg, E data) {
        return fail(FAIL, msg, data);
    }

    public static <E> RestResult<E> fail(Integer code, String msg, E data) {
        RestResult<E> bean = new RestResult<>();
        bean.code = (code == null || code.equals(SUCCESS)) ? FAIL : code;
        bean.msg = msg;
        bean.data = data;
        return bean;
    }

    @Override
    public String toString() {
        return "RestResult{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", data=" + data +
            '}';
    }
}
