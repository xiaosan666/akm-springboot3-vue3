package com.akm.springboot3.core.interceptor;

import com.akm.springboot3.core.annotation.ApiFreqLimit;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.*;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Objects;

/**
 * 接口请求频率检查
 *
 * @author xiaojun
 *
 */
@Component
public class ApiFreqInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiFreqInterceptor.class);
    private static final String NOT_CHECK_MARK = "-1";
    private final AkmConfig akmConfig;
    /**
     * 白名单
     */
    private final List<String> excludeUris;

    ApiFreqInterceptor(AkmConfig akmConfig) {
        this.akmConfig = akmConfig;
        this.excludeUris = ArrayUtil.merge(akmConfig.getFreqExcludeUrls(), akmConfig.getAlwaysExcludeUrls());
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String requestUri = request.getServletPath();
        if (Boolean.FALSE.equals(akmConfig.getEnabledApiFreq()) || isExcludeUri(requestUri)) {
            return true;
        }
        String cacheKey = getCacheKey(request);
        // 优先检查自定义频率限制注解，其次检查默认频率配置
        if (handler instanceof HandlerMethod) {
            ApiFreqLimit apiFreqLimit = ((HandlerMethod) handler).getMethodAnnotation(ApiFreqLimit.class);
            if (apiFreqLimit != null) {
                int time = apiFreqLimit.time();
                int limit = apiFreqLimit.limit();
                AssertUtils.isTrue(validFreq(cacheKey, time, limit), CodeMsg.API_FREQ_LIMIT);
                return true;
            }
        }

        // 根据api类型获取不同的频率配置
        boolean isOpApi = requestUri.contains(AkmConstants.OP_API);
        String freqStr = isOpApi ? akmConfig.getOpApiFreq() : akmConfig.getApiFreq();
        // 频率为-1不检查
        if (NOT_CHECK_MARK.equals(freqStr)) {
            return true;
        }
        String[] freqArr = freqStr.split(",");
        int time = Integer.parseInt(freqArr[0]);
        int limit = Integer.parseInt(freqArr[1]);
        AssertUtils.isTrue(validFreq(cacheKey, time, limit), CodeMsg.API_FREQ_LIMIT);
        return true;
    }

    private boolean isExcludeUri(String requestUri) {
        return StringUtils.pathMatch(this.excludeUris, requestUri);
    }

    /**
     * 获取api频率缓存key
     * 优先使用 requestUri + token， 若token不存在则使用 requestUri + ip地址
     */
    private String getCacheKey(HttpServletRequest request) {
        String requestUri = request.getServletPath();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(token)) {
            return RedisKeys.API_FREQ.concat(token).concat(RedisKeys.SEPARATOR).concat(requestUri);
        }
        return RedisKeys.API_FREQ.concat(Objects.requireNonNull(IpUtils.getClientIpAddress(request))).concat(RedisKeys.SEPARATOR).concat(requestUri);
    }

    /**
     * 验证请求频率
     *
     * @param cacheKey 缓存key
     * @param time     时间范围，单位秒
     * @param limit    最大次数
     */
    private boolean validFreq(String cacheKey, int time, int limit) {
        String s = StringCacheUtils.get(cacheKey);
        if (s == null) {
            StringCacheUtils.set(cacheKey, String.valueOf(limit - 1), time);
            return true;
        }
        int count = Integer.parseInt(s);
        if (count > 0) {
            StringCacheUtils.set(cacheKey, String.valueOf(count - 1), time);
            return true;
        }
        logger.warn("请求频率超过限制，cacheKey：{}，time：{}，最大次数：{}", cacheKey, time, limit);
        return false;
    }

}
