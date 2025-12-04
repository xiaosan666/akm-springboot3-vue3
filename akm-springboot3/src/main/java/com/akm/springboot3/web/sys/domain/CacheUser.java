package com.akm.springboot3.web.sys.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 缓存中的用户信息
 */
@Getter
@Setter
public class CacheUser {
    private String token;
    private String tenantId;
    private String tenantCode;
    private String clientType;
    private String userId;
    private String username;
    private String realname;
    private String orgId;
    private List<SysRoleBaseInfo> roleList;
}
