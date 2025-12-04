package com.akm.springboot3.core.exception;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.domain.RestResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.StringJoiner;

/**
 * 全局异常处理类，按请求过程异常大概分三种情况：
 * 1. 在进入Controller之前，譬如请求一个不存在的地址，404错误或在过滤器中抛出异常。
 * 2. 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误或在拦截器中抛出异常。
 * 3. 以上都正常时，在controller里执行逻辑代码时出的异常。譬如NullPointerException。
 * 这里使用@Controller加@ResponseBody而不用@RestController，是不想让swagger扫描到，目前swagger针对@RestController进行扫描
 *
 * @author xiaojun
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * post方式提交json数据,使用@Validated参数校验失败后,会抛出一个MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 将所有的错误提示使用";"拼接起来并返回
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return RestResult.fail(CodeMsg.INVALID_PARAMETER.getCode(), sj.toString());
    }

    /**
     * get方式提交参数,使用@Validated参数校验失败后,会抛出一个ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult<Object> handleConstraintViolationException(ConstraintViolationException e) {
        StringJoiner sj = new StringJoiner(";");
        e.getConstraintViolations().forEach(x -> sj.add(x.getMessage()));
        return RestResult.fail(CodeMsg.INVALID_PARAMETER.getCode(), sj.toString());
    }

    @ExceptionHandler(BindException.class)
    public RestResult<Object> handleBindException(BindException e) {
        // 将所有的错误提示使用";"拼接起来并返回
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return RestResult.fail(CodeMsg.INVALID_PARAMETER.getCode(), sj.toString());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public RestResult<Object> httpMessageNotReadableException(HttpServletRequest request, Exception ex) {
        this.consoleLog(request, ex);
        return RestResult.fail(CodeMsg.INVALID_PARAMETER.getCode(), CodeMsg.INVALID_PARAMETER.getMsg());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public RestResult<String> noHandlerFoundException(HttpServletRequest request, Exception ex) {
        this.consoleLog(request, ex);
        String msg = String.format("未找到处理方法，请求地址可能不正确（[%s]）", request.getServletPath());
        return RestResult.fail(CodeMsg.NOT_FOUND.getCode(), msg);
    }

    @ExceptionHandler({ServletException.class})
    public RestResult<String> servletException(HttpServletRequest request, ServletException ex) {
        // 获取根本原因并处理
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof BusinessException) {
            return RestResult.fail(((BusinessException) rootCause).getCode(), rootCause.getMessage());
        }
        return RestResult.fail(CodeMsg.NOT_FOUND);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public RestResult<String> httpRequestMethodNotSupportedException(HttpServletRequest request, Exception ex) {
        this.consoleLog(request, ex);
        String msg = String.format("请求方法[%s]不被支持", request.getMethod());
        return RestResult.fail(CodeMsg.METHOD_NOT_ALLOWED.getCode(), msg);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public RestResult<String> dataIntegrityViolationException(HttpServletRequest request, Exception ex) {
        this.consoleLog(request, ex);
        return RestResult.fail("数据库执行操作异常");
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public RestResult<Object> maxUploadSizeExceededException(HttpServletRequest request, Exception ex) {
        this.consoleLog(request, ex);
        return RestResult.fail(CodeMsg.PAYLOAD_TOO_LARGE);
    }

    /**
     * 自定义的业务异常，返回的msg可用于前端展示
     * 返回状态码: 200
     */
    @ExceptionHandler({BusinessException.class})
    public RestResult<Dict> businessException(HttpServletRequest request, BusinessException ex) {
        this.consoleLog(request, ex);
        String reqId = (String) request.getAttribute(AkmConstants.HTTP_HEADER_REQ_ID);
        return RestResult.fail(ex.getCode(), ex.getMessage(), Dict.create().set(AkmConstants.HTTP_HEADER_REQ_ID, reqId));
    }

    /**
     * 处理其他运行时发生的异常
     */
    @ExceptionHandler({Exception.class})
    public RestResult<Dict> exception(HttpServletRequest request, Exception ex) {
        this.consoleLog(request, ex);
        log.error("throw Exception", ex);
        String msg = "请求发生异常";
        String reqId = (String) request.getAttribute(AkmConstants.HTTP_HEADER_REQ_ID);
        return RestResult.fail(msg, Dict.create().set(AkmConstants.HTTP_HEADER_REQ_ID, reqId));
    }

    private <T extends Throwable> void consoleLog(HttpServletRequest request, T ex) {
        String reqId = (String) request.getAttribute(AkmConstants.HTTP_HEADER_REQ_ID);
        String reqUri = request.getServletPath();
        String method = request.getMethod();
        String msg = ex.getMessage();
        String message = CharSequenceUtil.format("--------ReqId:{},RequestURI:{},ReqMethod:{},ExMessage:{}", reqId, reqUri, method, msg);
        log.error(message);
    }

}
