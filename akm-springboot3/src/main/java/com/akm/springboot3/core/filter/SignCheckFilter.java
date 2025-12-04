package com.akm.springboot3.core.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.EncryptUtils;
import com.akm.springboot3.core.utils.RequestBodyReaderDecryptWrapper;
import com.akm.springboot3.core.utils.StringCacheUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.file.utils.FileUploadUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.*;

import static com.akm.springboot3.core.constant.AkmConstants.*;

/**
 * 请求签名拦截过滤器
 *
 * @author xiaojun
 *
 */
public class SignCheckFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SignCheckFilter.class);

    /**
     * 是否启用过滤器校验
     */
    private boolean enabled;

    /**
     * 白名单
     */
    private List<String> excludeUris = new ArrayList<>();

    /**
     * 签名有效时间范围，单位秒
     */
    private int timeTolerance;

    @Override
    public void init(FilterConfig filterConfig) {
        this.enabled = "1".equals(filterConfig.getInitParameter("enabled"));
        this.timeTolerance = Integer.parseInt(filterConfig.getInitParameter("timeTolerance"));
        String excludeUri = filterConfig.getInitParameter("excludeUri");
        if (StringUtils.isNotBlank(excludeUri)) {
            this.excludeUris = Arrays.asList(excludeUri.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!this.enabled || isExcludeUri(request.getServletPath())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String uri = request.getServletPath();
            // 时间戳
            String t = request.getHeader(HTTP_HEADER_TIMESTAMP);
            // 随机数
            String r = request.getHeader(HTTP_HEADER_RANDOM);
            // 下标，用于获取解密key
            String p = request.getHeader(HTTP_HEADER_P);
            if (StringUtils.isBlank(t) || StringUtils.isBlank(r) || StringUtils.isBlank(p)) {
                logger.warn("msg{},签名参数为空：t:{},r:{},p:{}", CodeMsg.SIGN_ERROR.getMsg(), t, r, p);
                throw new BusinessException(CodeMsg.SIGN_ERROR);
            }
            // 1秒
            long milliseconds = 1000;
            // 判断请求签名是否过期（时间戳与服务器时间超过阈值则认为过期）
            if (Math.abs(System.currentTimeMillis() - Long.parseLong(t)) > this.timeTolerance * milliseconds) {
                logger.warn("{},请求过期", CodeMsg.SIGN_ERROR.getMsg());
                throw new BusinessException(CodeMsg.SIGN_ERROR);
            }
            // 判断是否重复请求（在一定时间内，随机数出现过，则认为请求重复）
            if (Boolean.TRUE.equals(StringCacheUtils.isMemberBySet(RedisKeys.REQUEST_SIGN_SET, r))) {
                logger.warn("msg{},请求重复,key:{},value:{}", CodeMsg.SIGN_ERROR.getMsg(), RedisKeys.REQUEST_SIGN_SET, r);
                throw new BusinessException(CodeMsg.SIGN_ERROR);
            } else {
                StringCacheUtils.setSet(RedisKeys.REQUEST_SIGN_SET, r, this.timeTolerance);
            }

            RequestBodyReaderDecryptWrapper requestWrapper = null;
            // 文件上传，以空字符串来计算签名
            String bodyStr = "";
            if (!FileUploadUtils.isFileUploadRequest(request)) {
                requestWrapper = new RequestBodyReaderDecryptWrapper(request, false, null);
                // 把参数转为字符串
                bodyStr = this.getBodyParamStr(requestWrapper.getBody(), request);
            }

            // 待加密的签名字符串
            String str = uri.concat(t).concat(r).concat(bodyStr);
            String key = getKey(r, p);
            String s = EncryptUtils.hmacSha256(str, key);
            // 签名对比
            if (!s.equals(request.getHeader(HTTP_HEADER_SIGN))) {
                logger.warn("msg：{}，签名不正确，签名参数：uri:{},t:{},r:{},p:{},s:{}", CodeMsg.SIGN_ERROR.getMsg(), uri, t, r, p, request.getHeader(HTTP_HEADER_SIGN));
                logger.warn("计算签名：str:{},key:{},s{}", str, key, s);
                throw new BusinessException(CodeMsg.SIGN_ERROR);
            }
            filterChain.doFilter(requestWrapper == null ? servletRequest : requestWrapper, servletResponse);
        }
    }

    private boolean isExcludeUri(String requestUri) {
        return StringUtils.pathMatch(this.excludeUris, requestUri);
    }

    /**
     * 把p的每一位字符从32进制转为10进制，每个10进制数字作为r的下标取字符，最终会组成16位长度的k
     */
    private String getKey(String r, String p) {
        StringBuilder aesKey = new StringBuilder();
        for (String s : p.split("")) {
            int index = Integer.parseInt(s, 32);
            aesKey.append(r.charAt(index));
        }
        return aesKey.toString();
    }

    private String getBodyParamStr(String bodyStr, HttpServletRequest request) {
        if (StringUtils.isBlank(bodyStr)) {
            return "";
        }
        String contentType = request.getContentType();
        if (contentType == null) {
            return "";
        }
        // application/json
        if (contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)) {
            return bodyStr;
        }
        // application/x-www-form-urlencoded
        if (contentType.toLowerCase().contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            StringBuilder builder = new StringBuilder();
            JSONObject jsonObject = JSONUtil.parseObj(bodyStr);
            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                builder.append(entry.getKey());
                if (entry.getValue() != null) {
                    builder.append(entry.getValue());
                }
            }
            return builder.toString();
        }
        return "";
    }

    @Override
    public void destroy() {
    }

}
