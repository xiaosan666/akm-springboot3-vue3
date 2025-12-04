package com.akm.springboot3.core.annotation;


import java.lang.annotation.*;

/**
 * CSRF Token检查，防止表单重复提交
 *
 * @author xiaojun
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckCsrfToken {

    /**
     * 是否启用校验，默认true
     */
    boolean value() default true;

}
