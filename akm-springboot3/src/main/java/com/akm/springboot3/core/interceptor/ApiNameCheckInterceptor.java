package com.akm.springboot3.core.interceptor;

import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.ArrayUtil;
import com.akm.springboot3.core.utils.StringUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * 接口命名规范检查
 *
 * @author xiaojun
 *
 */
@Component
public class ApiNameCheckInterceptor implements HandlerInterceptor {

    private final AkmConfig akmConfig;

    /**
     * 白名单
     */
    private final List<String> excludeUris;


    ApiNameCheckInterceptor(AkmConfig akmConfig) {
        this.akmConfig = akmConfig;
        this.excludeUris = ArrayUtil.merge(akmConfig.getApiNameCheckExcludeUrls(), akmConfig.getAlwaysExcludeUrls());
        this.excludeUris.add("/login");
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String requestUri = request.getServletPath();
        if (Boolean.FALSE.equals(akmConfig.getEnabledApiNameCheck()) || isExcludeUri(requestUri) || nameValid(requestUri)) {
            return true;
        }
        throw new BusinessException(CodeMsg.API_NAME_INVALID);
    }

    private boolean isExcludeUri(String requestUri) {
        return StringUtils.pathMatch(this.excludeUris, requestUri);
    }

    /**
     * 校验命名是否符合约定，所有uri必须包含`/view/`或`/op/`或`/open/`或`/public/`
     */
    private boolean nameValid(String uri) {
        return uri.contains(AkmConstants.VIEW_API) || uri.contains(AkmConstants.OPEN_API) || uri.contains(AkmConstants.PUBLIC_API) || uri.contains(AkmConstants.OP_API);
    }
}
