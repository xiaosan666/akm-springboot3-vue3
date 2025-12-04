package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.web.sys.entity.SysApi;
import com.akm.springboot3.web.sys.service.SysApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "sys-接口管理")
@RestController
@RequestMapping("/auth/sys/api")
public class SysApiApi {

    private final SysApiService sysApiService;

    SysApiApi(SysApiService service) {
        this.sysApiService = service;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody SysApi record) {
        return sysApiService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "查询所有api")
    @PostMapping("/view/findAll")
    public List<SysApi> findAll() {
        return sysApiService.findByAll(new SysApi());
    }

    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return sysApiService.batchDel(idList);
    }

}
