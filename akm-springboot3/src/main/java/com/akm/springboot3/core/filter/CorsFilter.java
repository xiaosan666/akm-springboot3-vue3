package com.akm.springboot3.core.filter;

import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.core.utils.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用Filter允许跨域请求
 * 注：由于Filter的执行顺序在Interceptor之前，若使用拦截器允许跨域，
 * 许多异常在进入拦截器之前发生，这时候前端接收到的是跨域错误，而不是真正的异常内容
 *
 * @author xiaojun
 *
 */
@Slf4j
public class CorsFilter implements Filter {

    /**
     * 是否启用过滤器校验
     */
    private boolean enabled;

    /**
     * 白名单
     */
    private List<String> excludeUris = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        this.enabled = "1".equals(filterConfig.getInitParameter("enabled"));
        String allowDomainStr = filterConfig.getInitParameter("excludeUri");
        if (StringUtils.isNotBlank(allowDomainStr)) {
            this.excludeUris = Arrays.asList(allowDomainStr.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (this.enabled) {
            String originHeader = request.getHeader("Origin");
            AssertUtils.isTrue(!"*".equals(originHeader), "Origin不能为*");
            if (!excludeUris.contains(originHeader)) {
                log.error("Origin不在白名单，Origin:{}", originHeader);
                throw new BusinessException("Origin不在白名单");
            }
            for (String url : this.excludeUris) {
                if (url.equals(originHeader)) {
                    // 允许访问的域名，请在配置文件配置，不允许设置为*
                    response.setHeader("Access-Control-Allow-Origin", url);
                    // 是否允许携带cookie
                    response.setHeader("Access-Control-Allow-Credentials", "false");
                    // 允许的请求访问方式
                    response.setHeader("Access-Control-Allow-Methods", "GET,POST");
                    // 1个小时内无需发起预检请求
                    response.setHeader("Access-Control-Max-Age", "3600");
                    // 允许发送的请求头
                    response.setHeader("Access-Control-Allow-Headers", "*");
                    // 允许读取的响应头
                    response.setHeader("Access-Control-Expose-Headers", "k");
                    break;
                }
            }
            // 如果是OPTIONS则结束请求
            if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
