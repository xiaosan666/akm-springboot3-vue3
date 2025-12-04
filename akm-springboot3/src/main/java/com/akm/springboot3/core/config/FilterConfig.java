package com.akm.springboot3.core.config;

import com.akm.springboot3.core.filter.*;
import com.akm.springboot3.core.utils.ArrayUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 过滤器配置
 *
 * @author xiaojun
 *
 */
@Configuration
public class FilterConfig {

    private final AkmConfig akmConfig;

    FilterConfig(AkmConfig akmConfig) {
        this.akmConfig = akmConfig;
    }

    /**
     * 捕获filter异常
     * 该filter优先级必须为最高
     */
    @Bean
    public FilterRegistrationBean<CatchExceptionFilter> registrationCatchExceptionFilter() {
        FilterRegistrationBean<CatchExceptionFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CatchExceptionFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        return bean;
    }

    /**
     * TRACE请求过滤器
     * 拦截并拒绝所有TRACE请求
     */
    @Bean
    public FilterRegistrationBean<TraceFilter> registrationTraceFilter() {
        FilterRegistrationBean<TraceFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TraceFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        // 始终启用TRACE过滤器，不提供禁用选项以增强安全性
        Map<String, String> initParameter = new HashMap<>(1);
        initParameter.put("enabled", "1");
        bean.setInitParameters(initParameter);
        return bean;
    }

    /**
     * 允许跨域过滤器
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> registrationCorsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CorsFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        bean.setInitParameters(getInitParameter(akmConfig.getEnabledCorsAllow(), akmConfig.getCorsAllowDomain()));
        return bean;
    }

    /**
     * 参数解密
     */
    @Bean
    public FilterRegistrationBean<ParamsDecryptFilter> registrationParamsDecryptFilter() {
        FilterRegistrationBean<ParamsDecryptFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ParamsDecryptFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(2);
        List<String> excludeUrls = ArrayUtil.merge(akmConfig.getEncryptExcludeUrls(), akmConfig.getAlwaysExcludeUrls());
        bean.setInitParameters(getInitParameter(akmConfig.getEnabledEncrypt(), excludeUrls));
        return bean;
    }

    /**
     * 请求签名校验
     */
    @Bean
    public FilterRegistrationBean<SignCheckFilter> registrationSignCheckFilter() {
        FilterRegistrationBean<SignCheckFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new SignCheckFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(3);
        List<String> excludeUrls = ArrayUtil.merge(akmConfig.getSignExcludeUrls(), akmConfig.getAlwaysExcludeUrls());
        Map<String, String> initParameter = getInitParameter(akmConfig.getEnabledSign(), excludeUrls);
        initParameter.put("timeTolerance", String.valueOf(akmConfig.getSignTimeTolerance()));
        bean.setInitParameters(initParameter);
        return bean;
    }

    private Map<String, String> getInitParameter(boolean enable, List<String> excludeUri) {
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put("enabled", enable ? "1" : "0");
        initParameters.put("excludeUri", String.join(",", excludeUri));
        return initParameters;
    }

}
