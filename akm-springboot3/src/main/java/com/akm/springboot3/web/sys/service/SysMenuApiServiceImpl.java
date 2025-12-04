package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.entity.SysMenuApi;
import com.akm.springboot3.web.sys.mapper.SysMenuApiMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SysMenuApiServiceImpl implements SysMenuApiService {

    @Resource
    private SysMenuApiMapper sysMenuApiMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateApiByMenuId(List<String> uriList, String menuId) {
        sysMenuApiMapper.deleteByMenuId(menuId);
        if (!uriList.isEmpty()) {
            List<SysMenuApi> list = new ArrayList<>();
            for (String uri : uriList) {
                SysMenuApi bean = new SysMenuApi();
                bean.setId(SnowflakeUtils.id());
                bean.setMenuId(menuId);
                bean.setUri(uri);
                bean.setCreateUserId(UserThreadUtils.getUserId());
                bean.setCreateTime(new Date());
                list.add(bean);
            }
            sysMenuApiMapper.batchInsert(list);
        }
        // 更新资源对应接口，需要删除角色资源缓存数据
        CacheUtils.delByPattern(RedisKeys.ROLE_URI);
        return uriList.size();
    }
}
