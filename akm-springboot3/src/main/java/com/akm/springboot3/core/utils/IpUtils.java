package com.akm.springboot3.core.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class IpUtils {

    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String SEPARATOR = ",";

    // 常见的代理头字段，按优先级排序
    private static final List<String> HEADERS_TO_TRY = Arrays.asList(
        "X-Forwarded-For",
        "X-Real-IP",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP",
        "HTTP_X_FORWARDED_FOR"
    );

    /**
     * 获取客户端真实IP地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = null;

        // 1. 按优先级检查各个头部字段
        for (String header : HEADERS_TO_TRY) {
            ip = request.getHeader(header);
            if (isValidIp(ip)) {
                break;
            }
        }

        // 2. 如果头部字段中没有有效的IP，使用getRemoteAddr()
        if (!isValidIp(ip)) {
            ip = request.getRemoteAddr();
        }

        // 3. 处理多个IP的情况（如X-Forwarded-For: client, proxy1, proxy2）
        if (StringUtils.hasText(ip) && ip.contains(SEPARATOR)) {
            ip = ip.split(SEPARATOR)[0].trim();
        }

        // 4. 处理本地地址
        if (LOCALHOST_IPV6.equals(ip)) {
            ip = LOCALHOST_IP;
        }

        return isValidIp(ip) ? ip : null;
    }

    /**
     * 验证IP地址是否有效
     */
    private static boolean isValidIp(String ip) {
        return StringUtils.hasText(ip) && !UNKNOWN.equalsIgnoreCase(ip);
    }


}
