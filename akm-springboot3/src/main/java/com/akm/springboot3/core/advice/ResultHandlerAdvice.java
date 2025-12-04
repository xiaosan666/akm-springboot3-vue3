package com.akm.springboot3.core.advice;

import com.akm.springboot3.core.annotation.IgnoreResultHandler;
import com.akm.springboot3.core.domain.RestResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * 使接口统一返回RestResult
 *
 * @author xiaojun
 *
 */
@ControllerAdvice(annotations = RestController.class)
@Order(2)
public class ResultHandlerAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(ResultHandlerAdvice.class);

    /**
     * 这个方法表示对于哪些请求要执行beforeBodyWrite，返回true执行，返回false不执行
     */
    @Override
    public boolean supports(@Nonnull MethodParameter methodParameter, @Nonnull Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        if (method == null) {
            return true;
        }

        // 排除 SpringDoc 的接口，不进行统一封装
        String declaringClassName = method.getDeclaringClass().getName();
        if (declaringClassName.startsWith("org.springdoc")) {
            return false;
        }

        return method.getAnnotation(IgnoreResultHandler.class) == null && !RestResult.class.equals(method.getReturnType());
    }


    /**
     * 接口返回结果统一包装为RestResult
     */
    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, @Nonnull MethodParameter methodParameter, @Nonnull MediaType mediaType,
                                  @Nonnull Class<? extends HttpMessageConverter<?>> aClass, @Nonnull ServerHttpRequest serverHttpRequest, @Nonnull ServerHttpResponse serverHttpResponse) {
        // 参数aClass为org.springframework.http.converter.StringHttpMessageConverter
        // 接口返回String类型，Sting类型通过StringHttpMessageConverter不能直接转为RestResult对象，所以单独处理
        Method method = methodParameter.getMethod();
        if (method != null && String.class.equals(method.getReturnType())) {
            try {
                return new ObjectMapper().writeValueAsString(RestResult.success(body == null ? "" : body));
            } catch (JsonProcessingException e) {
                logger.error("类型转换失败", e);
            }
        }
        return RestResult.success(body);
    }
}


