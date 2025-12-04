package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.datascope.DataScopeService;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.entity.SysUserRole;
import com.akm.springboot3.web.sys.mapper.SysUserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private DataScopeService dataScopeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateRoleByUserId(List<String> roleIdList, String userId) {
        // 断言数据权限
        dataScopeService.assertUserId(userId);
        sysUserRoleMapper.deleteByUserId(userId);
        if (!roleIdList.isEmpty()) {
            List<SysUserRole> list = new ArrayList<>();
            for (String roleId : roleIdList) {
                SysUserRole bean = new SysUserRole();
                bean.setId(SnowflakeUtils.id());
                bean.setUserId(userId);
                bean.setRoleId(roleId);
                bean.setCreateUserId(UserThreadUtils.getUserId());
                bean.setCreateTime(new Date());
                list.add(bean);
            }
            sysUserRoleMapper.batchInsert(list);
        }
        return roleIdList.size();
    }

    @Override
    public List<String> findByRoleId(String roleId) {
        return sysUserRoleMapper.findByRoleId(roleId);
    }

}
