package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.datascope.DataScopeService;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.entity.SysRoleMenu;
import com.akm.springboot3.web.sys.mapper.SysRoleMenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private DataScopeService dataScopeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMenuByRoleId(List<String> menuIdList, String roleId) {
        sysRoleMenuMapper.deleteByRoleId(roleId);
        if (!menuIdList.isEmpty()) {
            List<SysRoleMenu> list = new ArrayList<>();
            for (String menuId : menuIdList) {
                SysRoleMenu bean = new SysRoleMenu();
                bean.setId(SnowflakeUtils.id());
                bean.setRoleId(roleId);
                bean.setMenuId(menuId);
                bean.setCreateUserId(UserThreadUtils.getUserId());
                bean.setCreateTime(new Date());
                list.add(bean);
            }
            sysRoleMenuMapper.batchInsert(list);
        }
        // 更新角色资源，需要删除角色资源缓存数据
        CacheUtils.del(RedisKeys.ROLE_URI.concat(roleId));
        return menuIdList.size();
    }

    @Override
    public List<String> getMenuIdByUserId(String userId) {
        // 断言数据权限
        dataScopeService.assertUserId(userId);
        return sysRoleMenuMapper.getMenuIdByUserId(userId);
    }
}

