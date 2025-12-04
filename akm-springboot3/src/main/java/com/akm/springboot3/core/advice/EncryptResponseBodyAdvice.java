package com.akm.springboot3.core.advice;

import cn.hutool.core.util.IdUtil;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.domain.RestResult;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.ArrayUtil;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.EncryptUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;

/**
 * 加密响应体
 * 加密条件：
 * 1.配置文件enabled-encrypt为true
 * 2.方法有@ResponseBody注解或类有@RestController注解
 * 3.方法或类没有NotEncryptResponseBody注解
 *
 * @author xiaojun
 *
 */
@ControllerAdvice(annotations = RestController.class, basePackages = "org.ssssssss.magicapi.core.web")
@Order(1)
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private final AkmConfig akmConfig;

    EncryptResponseBodyAdvice(AkmConfig akmConfig) {
        this.akmConfig = akmConfig;
    }

    /**
     * 判断是否需要加密
     */
    @Override
    public boolean supports(@Nonnull MethodParameter methodParameter, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return akmConfig.getEnabledEncrypt();
    }

    /**
     * 加密逻辑
     * 1.先生成32位uuid，注：UUID 是由一组32位数的16进制数字所构成
     * 2.取uuid最后16位数，把每位数转为10进制，在uuid中取下标，组成16位的AES key
     * 3.使用AES key加密data
     * 4.使用PublicKey公钥加密uuid，生成k
     * 5.把k作为响应头，encryptData作为响应体返回给前端
     */
    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, @Nonnull MethodParameter parameter, @Nonnull MediaType selectedContentType, @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        try {
            if (body == null || this.isExcludeUri(((ServletServerHttpRequest) request).getServletRequest().getServletPath())) {
                return body;
            }

            String dataStr;
            RestResult<Object> res = null;
            // 接口返回RestResult，只取data进行加密；接口返回
            if (body instanceof RestResult) {
                res = RestResult.success(null);
                res.setCode(((RestResult<?>) body).getCode());
                res.setMsg(((RestResult<?>) body).getMsg());
                Object data = ((RestResult<?>) body).getData();
                dataStr = data instanceof String ? String.valueOf(data) : new ObjectMapper().writeValueAsString(data == null ? "" : data);
            } else if (body instanceof String || body instanceof Integer || body instanceof Long || body instanceof Boolean || body instanceof Double) {
                dataStr = String.valueOf(body);
            } else {
                dataStr = new ObjectMapper().writeValueAsString(body);
            }
            String uuid = IdUtil.simpleUUID();
            String[] uuidArr = uuid.split("");
            StringBuilder sb = new StringBuilder();
            // 取uuid后16位
            int last16 = 16;
            for (String s : uuid.substring(last16).split("")) {
                sb.append(uuidArr[Integer.parseInt(s, 16)]);
            }
            String aesKey = sb.toString();
            String encryptData = EncryptUtils.aesEncrypt(dataStr, aesKey);

            // 加密key
            Object o = CacheUtils.get(RedisKeys.RSA_KEY_PAIR);
            @SuppressWarnings("unchecked")
            Map<String, String> cacheMap = (Map<String, String>) o;
            String publicKey2 = cacheMap.get("publicKey2");
            String encryptKey = EncryptUtils.rsaEncrypt(uuid, publicKey2);

            // 把加密后的key放到响应头
            HttpHeaders headers = response.getHeaders();
            headers.set(AkmConstants.HTTP_HEADER_AES_KEY, encryptKey);
            if (res == null) {
                return encryptData;
            }
            res.setData(encryptData);
            return res;
        } catch (Exception e) {
            logger.warn(CodeMsg.ENCRYPTION_ERROR.getMsg());
            throw new BusinessException(CodeMsg.ENCRYPTION_ERROR);
        }
    }

    private boolean isExcludeUri(String requestUri) {
        List<String> encryptExcludeUrls = ArrayUtil.merge(akmConfig.getEncryptExcludeUrls(), akmConfig.getAlwaysExcludeUrls());
        return StringUtils.pathMatch(encryptExcludeUrls, requestUri);
    }

}
