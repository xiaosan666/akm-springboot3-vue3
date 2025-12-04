package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.datascope.DataScopeOrgEnum;
import com.akm.springboot3.core.datascope.DataScopeService;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.entity.SysRole;
import com.akm.springboot3.web.sys.mapper.SysRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private DataScopeService dataScopeService;

    @Override
    public int insertOrUpdateSelective(SysRole record) {
        int num;
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(new Date());
            // 新增角色默认"仅自己"数据权限
            record.setDataScopeOrg(DataScopeOrgEnum.ONLY_MYSELF.getValue());
            num = sysRoleMapper.insertSelective(record);
        } else {
            record.setUpdateUserId(UserThreadUtils.getUserId());
            record.setUpdateTime(new Date());
            num = sysRoleMapper.updateByPrimaryKeySelective(record);
        }
        // 若不是平台管理员角色，强制设置租户为当前用户所属租户
        if (!UserThreadUtils.isPlatformAdmin()) {
            record.setTenantId(UserThreadUtils.getTenantId());
        }
        // 更新角色需要删除角色资源缓存数据
        if (StringUtils.isNotBlank(record.getId())) {
            CacheUtils.del(RedisKeys.ROLE_URI.concat(record.getId()));
        }
        return num;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDel(List<String> idList) {
        if (idList.isEmpty()) {
            return 0;
        }
        String tenantId = UserThreadUtils.isPlatformAdmin() ? null : UserThreadUtils.getTenantId();
        int num = sysRoleMapper.batchDel(idList, UserThreadUtils.getUserId(), tenantId);
        if (num != 0) {
            // 删除角色需要删除角色资源缓存数据
            for (String roleId : idList) {
                CacheUtils.del(RedisKeys.ROLE_URI.concat(roleId));
            }
        }
        return num;
    }

    @Override
    public List<SysRole> findAll(SysRole record) {
        // 若不是平台管理员角色，强制设置租户为当前用户所属租户
        if (!UserThreadUtils.isPlatformAdmin()) {
            record.setTenantId(UserThreadUtils.getTenantId());
        }
        record.setDelFlag(0);
        return sysRoleMapper.findByAll(record);
    }

    @Override
    public List<SysRoleBaseInfo> findRoleByUser(String userId) {
        // 断言数据权限
        dataScopeService.assertUserId(userId);
        return sysRoleMapper.findRoleByUser(userId);
    }

}




