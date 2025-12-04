package com.akm.springboot3.core.filter;

import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.EncryptUtils;
import com.akm.springboot3.core.utils.RequestBodyReaderDecryptWrapper;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.file.utils.FileUploadUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 请求参数解密
 *
 * @author xiaojun
 *
 */
public class ParamsDecryptFilter implements Filter {

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
        String excludeUri = filterConfig.getInitParameter("excludeUri");
        if (StringUtils.isNotBlank(excludeUri)) {
            this.excludeUris = Arrays.asList(excludeUri.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!this.enabled || isExcludeUri(request.getServletPath()) || FileUploadUtils.isFileUploadRequest(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String k = request.getHeader(AkmConstants.HTTP_HEADER_AES_KEY);
            if (StringUtils.isBlank(k)) {
                throw new BusinessException(CodeMsg.PRECONDITION_FAILED);
            }
            Map<String, String> cacheMap = CacheUtils.get(RedisKeys.RSA_KEY_PAIR);
            String privateKey = cacheMap.get("privateKey1");
            String aesKey;
            try {
                aesKey = EncryptUtils.rsaDecrypt(k, privateKey);
            } catch (Exception e) {
                // 解密失败原因一般是后端重新生成了秘钥对，前端未重新获取还在用的旧的秘钥
                throw new BusinessException(CodeMsg.PUBLIC_KEY_EXPIRED);
            }
            // 解密请求参数
            RequestBodyReaderDecryptWrapper requestWrapper = new RequestBodyReaderDecryptWrapper(request, true, aesKey);
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    private boolean isExcludeUri(String requestUri) {
        return StringUtils.pathMatch(this.excludeUris, requestUri);
    }

    @Override
    public void destroy() {
    }
}
