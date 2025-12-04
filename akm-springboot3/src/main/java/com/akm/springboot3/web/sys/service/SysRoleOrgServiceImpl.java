package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.config.CacheInitializer;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringCacheUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.entity.SysRoleOrg;
import com.akm.springboot3.web.sys.mapper.SysRoleMapper;
import com.akm.springboot3.web.sys.mapper.SysRoleOrgMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色自定义组织机构权限
 *
 * @author xiaojun
 *
 */
@Service
public class SysRoleOrgServiceImpl implements SysRoleOrgService {

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleOrgMapper sysRoleOrgMapper;

    private final CacheInitializer cacheInitializer;

    private final SysUserRoleService sysUserRoleService;

    SysRoleOrgServiceImpl(SysRoleMapper sysRoleMapper, SysRoleOrgMapper sysRoleOrgMapper, CacheInitializer cacheInitializer, SysUserRoleService sysUserRoleService) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleOrgMapper = sysRoleOrgMapper;
        this.cacheInitializer = cacheInitializer;
        this.sysUserRoleService = sysUserRoleService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateOrgDataScopeByRoleId(String roleId, String dataScopeOrg, List<String> orgIdList) {
        sysRoleOrgMapper.deleteByRoleId(roleId);
        if (!orgIdList.isEmpty()) {
            List<SysRoleOrg> list = new ArrayList<>();
            for (String orgId : orgIdList) {
                SysRoleOrg bean = new SysRoleOrg();
                bean.setId(SnowflakeUtils.id());
                bean.setRoleId(roleId);
                bean.setOrgId(orgId);
                bean.setCreateUserId(UserThreadUtils.getUserId());
                bean.setCreateTime(new Date());
                list.add(bean);
            }
            sysRoleOrgMapper.batchInsert(list);
        }
        sysRoleMapper.updateDataScorpOrgById(dataScopeOrg, roleId);
        // 更新缓存
        cacheInitializer.cacheRoleOrgData();
        // todo 修改用户角色权限信息，让用户重新登陆
        List<String> userIdList = sysUserRoleService.findByRoleId(roleId);
        for (String userId : userIdList) {
            String userIdTokenKey = RedisKeys.USER_ID_TOKEN.concat(userId);
            Set<String> tokenKeys = StringCacheUtils.keys(userIdTokenKey);
            for (String key : tokenKeys) {
                String oldToken = StringCacheUtils.getNoPrefix(key);
                if (oldToken != null) {
                    CacheUtils.del(RedisKeys.TOKEN.concat(oldToken));
                }
            }
        }
        return orgIdList.size();
    }

}

