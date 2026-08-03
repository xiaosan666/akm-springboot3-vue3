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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

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
    private Set<String> allowedOrigins = Collections.emptySet();

    /**
     * 自动白名单Host
     */
    private Set<String> autoAllowedHosts = Collections.emptySet();

    @Override
    public void init(FilterConfig filterConfig) {
        this.enabled = "1".equals(filterConfig.getInitParameter("enabled"));
        String allowDomainStr = filterConfig.getInitParameter("excludeUri");
        if (StringUtils.isNotBlank(allowDomainStr)) {
            this.allowedOrigins = Arrays.stream(allowDomainStr.split(","))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .peek(this::validateConfiguredOrigin)
                .collect(Collectors.toCollection(HashSet::new));
        }
        String autoAllowedOriginHosts = filterConfig.getInitParameter("autoAllowedOriginHosts");
        if (StringUtils.isNotBlank(autoAllowedOriginHosts)) {
            this.autoAllowedHosts = Arrays.stream(autoAllowedOriginHosts.split(","))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .map(host -> host.toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(HashSet::new));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 检查是否为TRACE请求
        if ("TRACE".equals(request.getMethod())) {
            // 拒绝TRACE请求，返回405状态码
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            response.getWriter().write("Method TRACE is not allowed");
            return;
        }
        if (this.enabled) {
            String originHeader = request.getHeader("Origin");
            AssertUtils.isTrue(!"*".equals(originHeader), "Origin不能为*");
            if (StringUtils.isNotBlank(originHeader)) {
                String normalizedOrigin = normalizeOrigin(originHeader);
                if (!isAllowedOrigin(normalizedOrigin)) {
                    // Origin校验失败，强制设置正确的Origin
                    response.setHeader("Access-Control-Allow-Origin", buildSafeAllowOrigin(request));
                    log.error("Origin不在白名单，Origin:{}", normalizedOrigin);
                    throw new BusinessException("Origin不在白名单");
                }
                response.setHeader("Access-Control-Allow-Origin", normalizedOrigin);
                response.setHeader("Vary", "Origin");
                // 允许的请求访问方式
                response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
                // 1个小时内无需发起预检请求
                response.setHeader("Access-Control-Max-Age", "3600");
                String requestHeaders = request.getHeader("Access-Control-Request-Headers");
                if (StringUtils.isNotBlank(requestHeaders)) {
                    response.setHeader("Access-Control-Allow-Headers", requestHeaders);
                }
                // 允许读取的响应头
                response.setHeader("Access-Control-Expose-Headers", "k");
            }
            // 如果是OPTIONS则结束请求
            if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private String normalizeOrigin(String originHeader) {
        String origin = originHeader.trim();
        AssertUtils.isTrue(origin.indexOf('\r') < 0 && origin.indexOf('\n') < 0, "Origin格式不合法");
        parseOrigin(origin);
        return origin;
    }

    private String buildSafeAllowOrigin(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (StringUtils.isNotBlank(referer)) {
            try {
                URI refererUri = new URI(referer.trim());
                if (StringUtils.isNotBlank(refererUri.getScheme()) && StringUtils.isNotBlank(refererUri.getHost())) {
                    return buildOrigin(refererUri.getScheme(), refererUri.getHost(), refererUri.getPort());
                }
            } catch (URISyntaxException e) {
                log.warn("Referer格式不合法，Referer:{}", referer);
            }
        }
        return buildOrigin(request.getScheme(), request.getServerName(), request.getServerPort());
    }

    private String buildOrigin(String scheme, String host, int port) {
        if (StringUtils.isBlank(scheme) || StringUtils.isBlank(host)) {
            return "";
        }
        StringBuilder origin = new StringBuilder();
        origin.append(scheme).append("://").append(host);
        if (shouldAppendPort(scheme, port)) {
            origin.append(":").append(port);
        }
        return origin.toString();
    }

    private boolean shouldAppendPort(String scheme, int port) {
        if (port <= 0) {
            return false;
        }
        return !("http".equalsIgnoreCase(scheme) && port == 80)
            && !("https".equalsIgnoreCase(scheme) && port == 443);
    }

    private boolean isAllowedOrigin(String origin) {
        if (allowedOrigins.contains(origin)) {
            return true;
        }
        URI uri = parseOrigin(origin);
        return isAutoAllowedHost(uri.getHost());
    }

    private boolean isAutoAllowedHost(String host) {
        if (StringUtils.isBlank(host)) {
            return false;
        }
        String normalizedHost = host.toLowerCase(Locale.ROOT);
        return autoAllowedHosts.stream().anyMatch(allowedHost -> matchesAutoAllowedHost(normalizedHost, allowedHost));
    }

    private boolean matchesAutoAllowedHost(String host, String allowedHost) {
        if (host.equals(allowedHost)) {
            return true;
        }

        int rangeSeparatorIndex = allowedHost.lastIndexOf('-');
        int ipSeparatorIndex = allowedHost.lastIndexOf('.');
        if (rangeSeparatorIndex < 0 || ipSeparatorIndex < 0 || rangeSeparatorIndex <= ipSeparatorIndex) {
            return false;
        }

        String prefix = allowedHost.substring(0, ipSeparatorIndex + 1);
        if (!host.startsWith(prefix)) {
            return false;
        }

        String rangeStartValue = allowedHost.substring(ipSeparatorIndex + 1, rangeSeparatorIndex);
        String rangeEndValue = allowedHost.substring(rangeSeparatorIndex + 1);
        String hostLastSegment = host.substring(prefix.length());
        if (!isNumeric(rangeStartValue) || !isNumeric(rangeEndValue) || !isNumeric(hostLastSegment)) {
            return false;
        }

        int rangeStart = Integer.parseInt(rangeStartValue);
        int rangeEnd = Integer.parseInt(rangeEndValue);
        int hostValue = Integer.parseInt(hostLastSegment);
        return hostValue >= rangeStart && hostValue <= rangeEnd;
    }

    private boolean isNumeric(String value) {
        return StringUtils.isNotBlank(value) && value.chars().allMatch(Character::isDigit);
    }

    private URI parseOrigin(String origin) {
        try {
            URI uri = new URI(origin);
            validateOriginUri(uri);
            return uri;
        } catch (URISyntaxException e) {
            throw new BusinessException("Origin格式不合法");
        }
    }

    private void validateConfiguredOrigin(String origin) {
        try {
            validateOriginUri(new URI(origin));
        } catch (URISyntaxException e) {
            throw new BusinessException("Origin配置不合法");
        }
    }

    private void validateOriginUri(URI uri) {
        AssertUtils.isTrue(StringUtils.isNotBlank(uri.getScheme()) && StringUtils.isNotBlank(uri.getHost()), "Origin配置不合法");
        AssertUtils.isTrue(uri.getPath() == null || uri.getPath().isEmpty() || "/".equals(uri.getPath()), "Origin配置不合法");
        AssertUtils.isTrue(uri.getQuery() == null && uri.getFragment() == null, "Origin配置不合法");
    }

}
