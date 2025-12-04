package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.web.sys.entity.SysTenant;
import com.akm.springboot3.web.sys.service.SysTenantService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "sys-租户管理")
@RestController
@RequestMapping("/auth/sys/tenant")
public class SysTenantApi {

    private final SysTenantService sysTenantService;

    SysTenantApi(SysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody SysTenant record) {
        return sysTenantService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findPage")
    public PageResult<SysTenant> findPage(@RequestBody PageQuery<SysTenant> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<SysTenant> list = sysTenantService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "列表查询")
    @PostMapping("/view/findAll")
    public List<SysTenant> findByAll(@RequestBody SysTenant record) {
        return sysTenantService.findByAll(record);
    }

    @Operation(summary = "根据编号删除用户")
    @PostMapping("/op/del")
    public int del(@RequestBody String id) {
        return sysTenantService.deleteByPrimaryKey(id);
    }

}
