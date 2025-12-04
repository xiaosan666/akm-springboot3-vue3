package com.akm.springboot3.core.advice;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.ArrayUtil;
import com.akm.springboot3.core.utils.StringUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对请求参数过滤，防止xss攻击
 * 1.去空格
 * 2.转义html字符，如 < 转为 &lt;
 * 3.判断有无敏感词
 *
 * @author xiaojun
 *
 */
@ControllerAdvice(annotations = RestController.class)
@Order(1)
public class FormatRequestBodyAdvice implements RequestBodyAdvice {

    private final String[] sensitiveWords;
    private final String sensitiveWordReg;

    private final HttpServletRequest request;

    private final AkmConfig akmConfig;

    FormatRequestBodyAdvice(HttpServletRequest request, AkmConfig akmConfig) {
        this.request = request;
        this.akmConfig = akmConfig;
        // 敏感词数组
        sensitiveWords = akmConfig.getSensitiveWord().split("\\|");
        // 敏感词正则表达式，形如：\s(or|delete)|(or|delete)\s
        sensitiveWordReg = "\\s(" + akmConfig.getSensitiveWord() + ")|(" + akmConfig.getSensitiveWord() + ")\\s";
    }

    @Override
    public boolean supports(@Nonnull MethodParameter methodParameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return !isExcludeUri(request.getServletPath());
    }

    @Override
    @Nonnull
    public HttpInputMessage beforeBodyRead(@Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    @Nonnull
    public Object afterBodyRead(@Nonnull Object body, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        if (body instanceof String) {
            return formatStringValue(body.toString());
        }
        if (body instanceof Integer || body instanceof Long || body instanceof Boolean || body instanceof Double) {
            return body;
        }
        JSON parse = JSONUtil.parse(body);
        if (parse instanceof JSONObject) {
            formatJsonObject((JSONObject) parse);
        } else if (parse instanceof JSONArray jsonArray) {
            formatJsonArray(jsonArray);
        }
        return parse.toBean(targetType);
    }


    @Override
    @Nullable
    public Object handleEmptyBody(@Nullable Object body, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 遍历对象
     */
    private void formatJsonObject(JSONObject jsonObject) {
        for (Map.Entry<String, Object> next : jsonObject.entrySet()) {
            Object value = next.getValue();
            if (value instanceof String) {
                next.setValue(formatStringValue((String) value));
            } else if (value instanceof JSONObject) {
                formatJsonObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                formatJsonArray((JSONArray) value);
            }
        }
    }

    /**
     * 遍历对象数组
     */
    private void formatJsonArray(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object object = jsonArray.get(i);
            if (object instanceof String) {
                jsonArray.set(i, formatStringValue((String) object));
            } else if (object instanceof JSONObject) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                formatJsonObject(jsonObject);
            }
        }
    }

    /**
     * 参数value去空格、转义、判断有无敏感词
     */
    private String formatStringValue(String value) {
        // 去空格并转义
        String filterValue = HtmlUtil.escape(CharSequenceUtil.trim(value));
        // 判断是否存在敏感词，存在则抛出异常
        if (hasSensitiveWord(filterValue)) {
            String msg = CharSequenceUtil.format("输入项中不能包含非法字符（{}）", getSensitiveWord(filterValue));
            throw new BusinessException(CodeMsg.SENSITIVE_WORD.getCode(), msg);
        }
        return filterValue;
    }

    /**
     * 获取敏感词
     */
    private String getSensitiveWord(String str) {
        for (String word : sensitiveWords) {
            if (str.toLowerCase().contains(word.toLowerCase())) {
                return word;
            }
        }
        return null;
    }

    /**
     * 判断有无敏感词
     */
    private boolean hasSensitiveWord(String str) {
        Pattern pattern = Pattern.compile(sensitiveWordReg, Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 是否白名单
     */
    private boolean isExcludeUri(String requestUri) {
        List<String> encryptExcludeUrls = ArrayUtil.merge(akmConfig.getSensitiveWordExcludeUrls(), akmConfig.getAlwaysExcludeUrls());
        return StringUtils.pathMatch(encryptExcludeUrls, requestUri);
    }
}
