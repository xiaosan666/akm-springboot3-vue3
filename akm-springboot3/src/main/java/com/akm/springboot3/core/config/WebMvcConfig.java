package com.akm.springboot3.core.config;

import com.akm.springboot3.core.interceptor.ApiFreqInterceptor;
import com.akm.springboot3.core.interceptor.ApiNameCheckInterceptor;
import com.akm.springboot3.core.interceptor.AuthInterceptor;
import com.akm.springboot3.core.interceptor.CsrfTokenInterceptor;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 在这里进行Spring MVC的配置, 如 interceptor, messageConverter, formatter, viewResolver等
 *
 * @author xiaojun
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private ApiFreqInterceptor apiFreqInterceptor;

    @Resource
    private ApiNameCheckInterceptor apiNameCheckInterceptor;

    @Resource
    private AuthInterceptor authInterceptor;

    @Resource
    private CsrfTokenInterceptor csrfTokenInterceptor;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        // 校验接口请求频率
        registry.addInterceptor(apiFreqInterceptor).addPathPatterns("/**");

        // 校验命名是否符合约定，所有uri必须包含`/view/`或`/op/`或`/open/`或`/public/`
        registry.addInterceptor(apiNameCheckInterceptor).addPathPatterns("/**");

        // 校验token、权限校验拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");

        // 校验CSRF Token
        registry.addInterceptor(csrfTokenInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
        // registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
