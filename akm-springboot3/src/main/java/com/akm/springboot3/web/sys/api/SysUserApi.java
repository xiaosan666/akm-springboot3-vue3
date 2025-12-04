package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.ApiFreqLimit;
import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.core.utils.*;
import com.akm.springboot3.web.sys.domain.SysUserDetail;
import com.akm.springboot3.web.sys.domain.SysUserInfo;
import com.akm.springboot3.web.sys.domain.SysUserUpdatePassword;
import com.akm.springboot3.web.sys.entity.SysUser;
import com.akm.springboot3.web.sys.service.SysUserRoleService;
import com.akm.springboot3.web.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Tag(name = "sys-用户管理")
@RestController
@RequestMapping("/auth/sys/user")
public class SysUserApi {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;

    SysUserApi(SysUserService sysUserService, SysUserRoleService sysUserRoleService) {
        this.sysUserService = sysUserService;
        this.sysUserRoleService = sysUserRoleService;
    }

    @Operation(summary = "新增/修改,返回用户id", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public String insertOrUpdate(@RequestBody SysUser record) {
        if (StringUtils.isBlank(record.getId())) {
            return sysUserService.insertSelective(record);
        } else {
            return sysUserService.updateByPrimaryKeySelective(record);
        }
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findPage")
    public PageResult<SysUserDetail> findPage(@RequestBody PageQuery<SysUserDetail> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<SysUserDetail> list = sysUserService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "根据编号删除用户")
    @PostMapping("/op/del")
    public int del(@RequestBody String id) {
        return sysUserService.deleteByPrimaryKey(id);
    }

    @Operation(summary = "登陆人获取自身信息")
    @PostMapping("/public/getLoginUserInfo")
    public SysUserInfo getLoginUserInfo() {
        String userId = UserThreadUtils.getUserId();
        return sysUserService.selectUserInfoById(userId);
    }

    @Operation(summary = "给用户分配角色")
    @PostMapping("/op/updateRoleByUserId")
    public int updateRoleByUserId(@RequestBody Map<String, Object> map) {
        String userId = (String) map.get("userId");
        @SuppressWarnings("unchecked")
        List<String> roleIdList = (List<String>) map.get("roleIdList");
        AssertUtils.notBlank(userId, "用户编号不能为空");
        // 给用户分配角色，删除用户token，使其重新登录
        Set<String> keys = StringCacheUtils.keys(RedisKeys.USER_ID_TOKEN.concat(userId));
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                String token = StringCacheUtils.getNoPrefix(key);
                // 删除token对应的用户信息缓存
                CacheUtils.del(RedisKeys.TOKEN.concat(token));
                // 删除userId对应的token
                StringCacheUtils.delNoPrefix(key);
            }
        }
        return sysUserRoleService.updateRoleByUserId(roleIdList, userId);
    }

    @Operation(summary = "普通用户修改密码")
    @PostMapping("/public/updatePassword")
    public int updatePassword(@RequestBody @Validated SysUserUpdatePassword params) {
        params.setId(UserThreadUtils.getUserId());
        return sysUserService.updatePassword(params, false);
    }

    @Operation(summary = "管理员修改密码")
    @PostMapping("/op/updatePassword")
    public int managerUpdatePassword(@RequestBody SysUserUpdatePassword params) {
        return sysUserService.updatePassword(params, true);
    }

    @Operation(summary = "重置锁定状态")
    @PostMapping("/op/resetLock")
    @ApiFreqLimit(time = 600, limit = 20)
    public void resetLock(@RequestBody String username) {
        SysUser sysUser = sysUserService.selectOneByUsername(username);
        AssertUtils.notNull(sysUser, "操作失败");
        String cacheKey = RedisKeys.PASSWORD_ERRORS.concat(username);
        StringCacheUtils.del(cacheKey);
    }

    @Operation(summary = "重置账号过期状态")
    @PostMapping("/op/resetExpired")
    @ApiFreqLimit(time = 600, limit = 20)
    public void resetExpired(@RequestBody String username) {
        SysUser sysUser = sysUserService.selectOneByUsername(username);
        AssertUtils.notNull(sysUser, "操作失败");
        sysUserService.updateExpiredTimeByUserId(sysUser.getId());
    }

    @Operation(summary = "用户更新头像")
    @PostMapping("/public/updateAvatar")
    public int updateAvatar(@Schema(title = "头像路径") @RequestBody String avatar) {
        return sysUserService.updateAvatar(UserThreadUtils.getUserId(), avatar);
    }
}
