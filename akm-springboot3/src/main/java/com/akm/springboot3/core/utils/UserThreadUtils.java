package com.akm.springboot3.core.utils;

import cn.hutool.core.collection.CollUtil;
import com.akm.springboot3.web.sys.domain.CacheUser;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;

import java.util.List;


public class UserThreadUtils {

    /**
     * 应用编码
     */
    public static final String CLIENT_TYPE = "clientType";
    /**
     * 用户登陆的token
     */
    public static final String TOKEN = "token";
    /**
     * 租户id
     */
    public static final String TENANT_ID = "tenantId";
    /**
     * 是否平台管理员
     */
    public static final String IS_PLATFORM_ADMIN = "is-platform-admin";
    /**
     * 用户id
     */
    public static final String USER_ID = "userId";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 用户姓名
     */
    public static final String REALNAME = "realname";
    /**
     * 用户归属组织机构
     */
    public static final String ORG_ID = "orgId";
    /**
     * 用户角色列表
     */
    public static final String ROLE_LIST = "roleList";
    /**
     * 角色编码：平台管理组
     */
    public static final String ROLE_TENANT_ADMIN_CODE = "tenant-admin-role-admin";

    private UserThreadUtils() {
        throw new IllegalStateException("UserThreadUtils Utility class");
    }

    public static void setUserInfo(CacheUser userInfo) {
        if (userInfo == null) {
            return;
        }
        List<SysRoleBaseInfo> roleList = userInfo.getRoleList();
        ThreadContext.set(CLIENT_TYPE, userInfo.getClientType());
        ThreadContext.set(TOKEN, userInfo.getToken());
        ThreadContext.set(TENANT_ID, userInfo.getTenantId());
        ThreadContext.set(USER_ID, userInfo.getUserId());
        ThreadContext.set(USERNAME, userInfo.getUsername());
        ThreadContext.set(REALNAME, userInfo.getRealname());
        ThreadContext.set(ORG_ID, userInfo.getOrgId());
        ThreadContext.set(ROLE_LIST, roleList);
        boolean isAdmin = CollUtil.contains(roleList, role -> ROLE_TENANT_ADMIN_CODE.equals(role.getCode()));
        ThreadContext.set(IS_PLATFORM_ADMIN, isAdmin);
    }

    public static String getToken() {
        return ThreadContext.get(TOKEN);
    }

    public static String getClientType() {
        return ThreadContext.get(CLIENT_TYPE);
    }

    public static String getUserId() {
        return ThreadContext.get(USER_ID);
    }

    public static String getTenantId() {
        return ThreadContext.get(TENANT_ID);
    }

    public static String getUserName() {
        return ThreadContext.get(USERNAME);
    }

    public static String getName() {
        return ThreadContext.get(REALNAME);
    }

    public static String getOrgId() {
        return ThreadContext.get(ORG_ID);
    }

    public static List<SysRoleBaseInfo> getRoleList() {
        return ThreadContext.get(ROLE_LIST);
    }

    public static boolean isPlatformAdmin() {
        Object o = ThreadContext.get(IS_PLATFORM_ADMIN);
        return o != null;
    }

    public static void clear() {
        ThreadContext.close();
    }
}
