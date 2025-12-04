package com.akm.springboot3.core.datascope;

import cn.hutool.core.collection.CollUtil;
import com.akm.springboot3.core.config.CacheInitializer;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.entity.SysUser;
import com.akm.springboot3.web.sys.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * 用户权限判断工具类
 */
@Component
@Slf4j
public class DataScopeService {


    private final CacheInitializer cacheInitializer;
    private final SysUserMapper sysUserMapper;

    DataScopeService(CacheInitializer cacheInitializer, SysUserMapper sysUserMapper) {
        this.cacheInitializer = cacheInitializer;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 断言登录用户是否有访问目标userId的权限
     *
     * @param userId
     */
    public void assertUserId(String userId) {
        // 未登录请求，不校验数据权限
        if (StringUtils.isBlank(UserThreadUtils.getToken())) {
            return;
        }
        // 访问"自己"数据
        if (UserThreadUtils.getUserId().equals(userId)) {
            return;
        }
        // 目标用户信息
        SysUser sysUser = sysUserMapper.selectOneById(userId);
        if (sysUser == null) {
            log.warn("没有访问权限，用户不存在，用户ID：{}，访问用户Id：{}", UserThreadUtils.getUserId(), userId);
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }
        this.assertOrg(sysUser.getOrgId());
    }

    /**
     * 断言登录用户是否有访问目标username的权限
     *
     * @param username
     */
    public void assertUserName(String username) {
        SysUser sysUser = sysUserMapper.selectOneByUsername(username);
        if (sysUser == null) {
            log.warn("没有访问权限，用户名不存在，用户ID：{}，访问用户名：{}", UserThreadUtils.getUserId(), username);
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }
        assertUserId(sysUser.getId());
    }

    /**
     * 断言登录用户是否有访问目标orgId的权限
     *
     * @param targetOrgId
     */
    public void assertOrg(String targetOrgId) {
        // 用户角色列表
        List<SysRoleBaseInfo> roleList = UserThreadUtils.getRoleList();
        String orgId = UserThreadUtils.getOrgId();
        // 组织机构权限编码，对应枚举类：DataScopeOrgEnum
        List<String> orgCodes = CollUtil.map(roleList, SysRoleBaseInfo::getDataScopeOrg, true);
        // 有DataScopeOrg注解，用户没有角色/权限编码，则拒绝访问
        if (orgCodes == null || orgCodes.isEmpty()) {
            log.warn("没有访问权限，登录用户角色编码为空，用户ID：{}", UserThreadUtils.getUserId());
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }
        // 组织机构权限编码包含"全部数据权限"
        if (orgCodes.contains(DataScopeOrgEnum.ALL.getValue())) {
            return;
        }
        // "仅本人"数据权限
        if (orgCodes.size() == 1 && orgCodes.get(0).equals(DataScopeOrgEnum.ONLY_MYSELF.getValue())) {
            log.warn("没有访问权限，登录用户仅限查看本人数据，用户ID：{}，无权查组织机构id权限: {}", UserThreadUtils.getUserId(), orgId);
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }
        // 用户组织机构权限集合
        HashSet<String> orgIdSet = getOrgIdListByDataScopeOrg(roleList, orgId);
        if (!orgIdSet.contains(targetOrgId)) {
            log.warn("没有访问权限，用户ID：{}，无权查组织机构id权限: {}", UserThreadUtils.getUserId(), targetOrgId);
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }
    }

    /**
     * 获取用户有权限访问的组织机构id集合
     *
     * @param roleList 角色列表
     * @param orgId    组织ID
     * @return 有权限访问的组织机构ID集合
     */
    public HashSet<String> getOrgIdListByDataScopeOrg(List<SysRoleBaseInfo> roleList, String orgId) {
        // 用户组织机构权限集合
        HashSet<String> orgIdSet = new HashSet<>();
        for (SysRoleBaseInfo role : roleList) {
            String dataScopeOrg = role.getDataScopeOrg();
            if (DataScopeOrgEnum.CUSTOM.getValue().equals(dataScopeOrg)) {
                // 自定义数据权限
                List<String> orgIdList = cacheInitializer.getOrgIdListByRoleId(role.getId());
                orgIdSet.addAll(orgIdList);
            } else if (DataScopeOrgEnum.DEPT.getValue().equals(dataScopeOrg)) {
                // 部门数据权限
                orgIdSet.add(orgId);
            } else if (DataScopeOrgEnum.DEPT_AND_BELOW.getValue().equals(dataScopeOrg)) {
                // 部门及以下数据权限
                List<String> orgInfoList = cacheInitializer.getChildrenOrgIdListByOrgId(orgId);
                orgIdSet.add(orgId);
                orgIdSet.addAll(orgInfoList);
            }
        }
        return orgIdSet;
    }

}
