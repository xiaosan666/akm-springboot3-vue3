package com.akm.springboot3.core.config;

import cn.hutool.core.collection.CollUtil;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.web.sys.domain.CacheOrg;
import com.akm.springboot3.web.sys.domain.CacheRoleOrg;
import com.akm.springboot3.web.sys.mapper.SysOrgMapper;
import com.akm.springboot3.web.sys.mapper.SysRoleOrgMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.akm.springboot3.core.constant.RedisKeys.SYS_ORG_LIST;
import static com.akm.springboot3.core.constant.RedisKeys.SYS_ROLE_ID_ORG_ID;


@Component
public class CacheInitializer implements CommandLineRunner {

    @Resource
    private SysRoleOrgMapper sysRoleOrgMapper;

    @Resource
    private SysOrgMapper sysOrgMapper;


    @Override
    public void run(String... args) {
        this.cacheOrgData();
        this.cacheRoleOrgData();
    }

    /**
     * 缓存组织机构
     */
    public void cacheOrgData() {
        List<CacheOrg> orgList = sysOrgMapper.findAll();
        CacheUtils.set(SYS_ORG_LIST, orgList);
    }

    /**
     * 缓存roleId与orgId关系数据（角色自定义组织机构数据权限）
     */
    public void cacheRoleOrgData() {
        List<CacheRoleOrg> roleOrgList = sysRoleOrgMapper.findAll();
        CacheUtils.set(SYS_ROLE_ID_ORG_ID, roleOrgList);
    }

    /**
     * 根据orgId从缓存中获取所有子级orgId
     *
     * @param orgId 组织ID
     * @return 子级组织ID列表
     */
    public List<String> getChildrenOrgIdListByOrgId(String orgId) {
        List<CacheOrg> orgList = CacheUtils.get(SYS_ORG_LIST);
        CacheOrg org = CollUtil.findOne(orgList, item -> item.getId().equals(orgId));
        return orgList.stream()
            .filter(item -> item.getOrgFullId() != null && item.getOrgFullId().startsWith(org.getOrgFullId()))
            .map(CacheOrg::getId)
            .collect(Collectors.toList());
    }

    /**
     * 根据roleId从缓存中获取关联的orgId
     *
     * @param roleId 角色ID
     * @return 组织ID列表
     */
    public List<String> getOrgIdListByRoleId(String roleId) {
        List<CacheRoleOrg> roleOrgList = CacheUtils.get(SYS_ROLE_ID_ORG_ID);
        return roleOrgList.stream()
            .filter(item -> item.getRoleId().equals(roleId))
            .map(CacheRoleOrg::getOrgId)
            .collect(Collectors.toList());
    }
}
