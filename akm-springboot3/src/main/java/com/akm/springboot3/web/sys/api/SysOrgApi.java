package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.web.sys.domain.SysOrgInfo;
import com.akm.springboot3.web.sys.entity.SysOrg;
import com.akm.springboot3.web.sys.service.SysOrgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "sys-组织机构管理")
@RestController
@RequestMapping("/auth/sys/org")
public class SysOrgApi {

    private final SysOrgService sysOrgService;

    SysOrgApi(SysOrgService sysOrgService) {
        this.sysOrgService = sysOrgService;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody @Validated SysOrg record) {
        return sysOrgService.insertOrUpdate(record);
    }

    @Operation(summary = "查询所有组织机构")
    @PostMapping("/view/findAll")
    public List<SysOrg> findAll(@RequestBody SysOrg sysOrg) {
        return sysOrgService.findByAll(sysOrg);
    }

    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return sysOrgService.batchDel(idList);
    }

    @Operation(summary = "根据orgId查询所有子集组织机构")
    @PostMapping("/public/selectChildById")
    public List<SysOrgInfo> selectChildById(@RequestBody String id) {
        return sysOrgService.selectChildById(id);
    }

}
