package com.akm.springboot3.web.biz.api;

import com.akm.springboot3.web.biz.entity.BizBanner;
import com.akm.springboot3.web.biz.service.BizBannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "biz-轮播图")
@RestController
@RequestMapping("/share/public/biz/banner")
@Validated
public class BannerApi {

    private final BizBannerService bizBannerService;

    BannerApi(BizBannerService service) {
        this.bizBannerService = service;
    }

    @Operation(summary = "获取轮播图列表")
    @PostMapping("/view/findByType")
    public List<BizBanner> findByType(@Schema(title = "轮播图分类", defaultValue = "banner_1") @RequestBody String type) {
        return bizBannerService.findByType(type);
    }

}
