package com.akm.springboot3.web.demo.api;

import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.web.demo.entity.DemoUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "demo-全局异常处理")
@RestController
@RequestMapping("/demo/exception/open")
@Slf4j
public class DemoExceptionApi {

    @Operation(summary = "自定义业务异常：BusinessException", description = "BusinessException：用于业务判断或try catch捕获后抛出的异常,前端可直接显示msg给用户")
    @PostMapping("/customer")
    public void customer() {
        throw new BusinessException("我是异常内容");
    }

    @Operation(summary = "测试参数转换异常", description = "参数输入非对象会导致参数解析失败")
    @PostMapping("/paramError")
    public void paramError(@RequestBody DemoUser demoUser) {
        log.info(JSONUtil.toJsonStr(demoUser));
    }

}
