package com.akm.springboot3.web.biz.api;

import com.akm.springboot3.web.biz.domain.AppCheckUpdate;
import com.akm.springboot3.web.biz.entity.BizAppVersion;
import com.akm.springboot3.web.biz.service.BizAppVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "biz-应用版本管理")
@RestController
@RequestMapping("/share/public/biz/app/version")
public class AppVersion {

    private final BizAppVersionService bizAppVersionService;

    AppVersion(BizAppVersionService bizAppVersionService) {
        this.bizAppVersionService = bizAppVersionService;
    }

    @Operation(summary = "查询版本记录")
    @PostMapping("/view/findAll")
    public List<BizAppVersion> findAll(@RequestBody BizAppVersion bizAppVersion) {
        return bizAppVersionService.findByAll(bizAppVersion);
    }


    @Operation(summary = "判断app是否有更新")
    @PostMapping("/view/checkUpdate")
    public BizAppVersion checkUpdate(@RequestBody AppCheckUpdate appCheckUpdate) {
        return bizAppVersionService.checkUpdate(appCheckUpdate);
    }

}
