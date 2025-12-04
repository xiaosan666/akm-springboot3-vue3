package com.akm.springboot3.core.annotation;


import java.lang.annotation.*;

/**
 * 接口频率：单位时间允许最大请求次数
 *
 * @author xiaojun
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiFreqLimit {

    /**
     * 时间，单位秒
     */
    int time() default 60;

    /**
     * 请求最大次数
     */
    int limit() default 10;

}
