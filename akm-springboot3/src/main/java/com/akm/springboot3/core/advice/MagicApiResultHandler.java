package com.akm.springboot3.core.advice;

import com.akm.springboot3.core.domain.RestResult;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.RequestEntity;
import org.ssssssss.magicapi.core.interceptor.ResultProvider;
import org.ssssssss.magicapi.modules.db.model.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MagicApiResultHandler implements ResultProvider {
    /**
     * 定义返回结果，默认返回JsonBean
     */
    @Override
    public Object buildResult(RequestEntity requestEntity, int code, String message, Object data) {
        // 如果对分页格式有要求的话，可以对data的类型进行判断，进而返回不同的格式
        return RestResult.SUCCESS == code ? RestResult.success(message, data) : RestResult.fail(message, data);
    }

    /**
     * 定义分页返回结果，该项会被封装在Json结果内，
     * 此方法可以不覆盖，默认返回PageResult
     */
    @Override
    public Object buildPageResult(RequestEntity requestEntity, Page page, long total, List<Map<String, Object>> data) {
        Map<String, Object> res = new HashMap<>(3);
        res.put("total", total);
        res.put("list", total == 0 ? new ArrayList<>() : data);
        return res;
    }
}
