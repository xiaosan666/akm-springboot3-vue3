package com.akm.springboot3.web.sys.api;

import cn.hutool.core.map.MapUtil;
import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.datascope.DataScopeOrgEnum;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.entity.SysRole;
import com.akm.springboot3.web.sys.service.SysRoleMenuService;
import com.akm.springboot3.web.sys.service.SysRoleOrgService;
import com.akm.springboot3.web.sys.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Tag(name = "sys-角色管理")
@RestController
@RequestMapping("/auth/sys/role")
public class SysRoleApi {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleOrgService sysRoleOrgService;

    SysRoleApi(SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService, SysRoleOrgService sysRoleOrgService) {
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
        this.sysRoleOrgService = sysRoleOrgService;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody SysRole record) {
        return sysRoleService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "查询所有角色")
    @PostMapping("/view/findAll")
    public List<SysRole> findAll(@RequestBody SysRole record) {
        return sysRoleService.findAll(record);
    }

    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return sysRoleService.batchDel(idList);
    }

    @Operation(summary = "给角色分配资源")
    @PostMapping("/op/updateMenuByRoleId")
    public int updateMenuByRoleId(@RequestBody Map<String, Object> map) {
        String roleId = (String) map.get("roleId");
        @SuppressWarnings("unchecked")
        List<String> menuIdList = (List<String>) map.get("menuIdList");
        AssertUtils.notBlank(roleId, "角色编号不能为空");
        return sysRoleMenuService.updateMenuByRoleId(menuIdList, roleId);
    }


    @Operation(summary = "给角色分配组织机构数据权限")
    @PostMapping("/op/updateOrgDataScopeByRoleId")
    public int updateOrgDataScopeByRoleId(@RequestBody Map<String, Object> map) {
        String roleId = MapUtil.getStr(map, "roleId");
        String dataScopeOrg = MapUtil.getStr(map, "dataScopeOrg", DataScopeOrgEnum.ONLY_MYSELF.getValue());
        @SuppressWarnings("unchecked")
        List<String> orgIdList = MapUtil.get(map, "orgIdList", List.class);
        AssertUtils.notBlank(roleId, "角色编号不能为空");
        return sysRoleOrgService.updateOrgDataScopeByRoleId(roleId, dataScopeOrg, orgIdList);
    }

    @Operation(summary = "查询用户对应的角色列表")
    @PostMapping("/view/findRoleByUser")
    public List<SysRoleBaseInfo> findRoleByUser(@RequestBody String userId) {
        return sysRoleService.findRoleByUser(userId);
    }

}
