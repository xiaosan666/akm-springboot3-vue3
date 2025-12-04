package com.akm.springboot3.core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * TRACE请求过滤器
 * 拦截并拒绝所有TRACE请求，返回405 Method Not Allowed状态码
 *
 * @author xiaojun
 */
public class TraceFilter implements Filter {

    /**
     * 是否启用过滤器校验
     */
    private boolean enabled;

    @Override
    public void init(FilterConfig filterConfig) {
        this.enabled = "1".equals(filterConfig.getInitParameter("enabled"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (this.enabled) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            // 检查是否为TRACE请求
            if ("TRACE".equals(request.getMethod())) {
                // 拒绝TRACE请求，返回405状态码
                response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                response.getWriter().write("Method TRACE is not allowed");
                return;
            }
        }
        // 非TRACE请求，继续过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // 清理资源（如果有）
    }
}
