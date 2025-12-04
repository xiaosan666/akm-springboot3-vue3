package com.akm.springboot3.web.biz.api;

import com.akm.springboot3.web.biz.domain.District;
import com.akm.springboot3.web.biz.service.BizDistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "biz-省市区数据管理")
@RestController
@RequestMapping("/share/public/biz/district")
public class DistrictApi {

    private final BizDistrictService bizDistrictService;

    DistrictApi(BizDistrictService bizDistrictService) {
        this.bizDistrictService = bizDistrictService;
    }

    @Operation(summary = "获取全部")
    @PostMapping("")
    public List<District> district() {
        return bizDistrictService.findDistrict(new District());
    }

    @Operation(summary = "查询")
    @PostMapping("/view/findDistrict")
    public List<District> findDistrict(@RequestBody District district) {
        return bizDistrictService.findDistrict(district);
    }

    @Operation(summary = "初始化")
    @PostMapping("/op/init")
    public void init() {
        bizDistrictService.init();
    }

}
