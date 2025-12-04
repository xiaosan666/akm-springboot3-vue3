package com.akm.springboot3.core.interceptor;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.StringCacheUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Csrf token校验
 *
 * @author xiaojun
 *
 */
@Component
public class CsrfTokenInterceptor implements HandlerInterceptor {

    /**
     * 1秒
     */
    private static final long INTERVAL = 1000;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (handler instanceof HandlerMethod) {
            CheckCsrfToken tokenCheck = ((HandlerMethod) handler).getMethodAnnotation(CheckCsrfToken.class);
            if (tokenCheck != null) {
                String userId = UserThreadUtils.getUserId();
                String headerToken = request.getHeader(AkmConstants.HTTP_HEADER_CSRF_TOKEN);
                String cacheToken = StringCacheUtils.get(RedisKeys.CSRF_TOKEN.concat(userId));
                if (headerToken == null || cacheToken == null) {
                    throw new BusinessException(CodeMsg.CSRF_TOKEN_ERROR);
                }
                String[] arr = cacheToken.split("_");
                if (!headerToken.equals(arr[0])) {
                    throw new BusinessException(CodeMsg.CSRF_TOKEN_ERROR);
                }
                long createTime = Long.parseLong(arr[1]);
                // CSRF Token创建时间在1秒内，则认为是无效请求
                if (System.currentTimeMillis() - createTime < INTERVAL) {
                    throw new BusinessException(CodeMsg.CSRF_TOKEN_ERROR.getCode(), "点击太快了");
                }
                StringCacheUtils.del(RedisKeys.CSRF_TOKEN.concat(userId));
            }
        }
        return true;
    }

}
