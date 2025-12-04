package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.web.sys.domain.SysMenuInfo;
import com.akm.springboot3.web.sys.entity.SysMenu;
import com.akm.springboot3.web.sys.service.SysMenuApiService;
import com.akm.springboot3.web.sys.service.SysMenuService;
import com.akm.springboot3.web.sys.service.SysRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Tag(name = "sys-菜单管理")
@RestController
@RequestMapping("/auth/sys/menu")
public class SysMenuApi {

    private final SysMenuService sysMenuService;
    private final SysMenuApiService sysMenuApiService;
    private final SysRoleMenuService sysRoleMenuService;

    SysMenuApi(SysMenuService sysMenuService, SysMenuApiService sysMenuApiService, SysRoleMenuService sysRoleMenuService) {
        this.sysMenuService = sysMenuService;
        this.sysMenuApiService = sysMenuApiService;
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody SysMenu record) {
        return sysMenuService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "查询所有资源")
    @PostMapping("/view/findAll")
    public List<SysMenu> findAll(@RequestBody SysMenu sysMenu) {
        return sysMenuService.findByAll(sysMenu);
    }

    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return sysMenuService.batchDel(idList);
    }

    @Operation(summary = "给资源分配api")
    @PostMapping("/op/updateApiByMenuId")
    public int updateApiByMenuId(@RequestBody Map<String, Object> map) {
        String menuId = (String) map.get("menuId");
        @SuppressWarnings("unchecked")
        List<String> uriList = (List<String>) map.get("uriList");
        AssertUtils.notBlank(menuId, "资源编号不能为空");
        return sysMenuApiService.updateApiByMenuId(uriList, menuId);
    }

    @Operation(summary = "查询登陆用户所拥有的资源")
    @PostMapping("/public/findLoginUserMenu")
    public List<SysMenuInfo> findMenu() {
        return sysMenuService.findMenu();
    }

    @Operation(summary = "查询用户的菜单id列表")
    @PostMapping("/view/getMenuIdByUserId")
    public List<String> getMenuIdByUserId(@RequestBody String userId) {
        return sysRoleMenuService.getMenuIdByUserId(userId);
    }
}
