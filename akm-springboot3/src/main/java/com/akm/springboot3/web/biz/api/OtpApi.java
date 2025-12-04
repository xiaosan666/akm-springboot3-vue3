package com.akm.springboot3.web.biz.api;

import com.akm.springboot3.web.biz.service.BizOtpLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "biz-otp二次验证")
@RestController
@RequestMapping("/share/public/biz/otp")
@Validated
public class OtpApi {

    private final BizOtpLogService bizOtpLogService;

    OtpApi(BizOtpLogService service) {
        this.bizOtpLogService = service;
    }

    @Operation(summary = "获取otp二维码数据")
    @PostMapping("/view/getQrCodeData")
    public String getQrCodeData() {
        return bizOtpLogService.getQrCodeData();
    }

    @Operation(summary = "检查otp验证码是否有效")
    @PostMapping("/op/verificationOtpCode")
    public void verificationOtpCode(@RequestBody Map<String, String> map) {
        bizOtpLogService.verificationOtpCode(map.get("otpCode"), map.get("btnCode"));
    }

}
