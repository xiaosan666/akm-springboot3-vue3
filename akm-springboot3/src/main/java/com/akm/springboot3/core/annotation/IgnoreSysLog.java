package com.akm.springboot3.core.annotation;

import java.lang.annotation.*;

/**
 * 接口返回结果会统一包装为RestResult，使用该注解则不会包装
 *
 * @author xiaojun
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSysLog {
}
