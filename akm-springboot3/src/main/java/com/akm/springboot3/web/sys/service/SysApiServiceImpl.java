package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.entity.SysApi;
import com.akm.springboot3.web.sys.mapper.SysApiMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SysApiServiceImpl implements SysApiService {

    @Resource
    private SysApiMapper sysApiMapper;

    @Override
    public int insertOrUpdateSelective(SysApi record) {
        int num;
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(new Date());
            num = sysApiMapper.insertSelective(record);
        } else {
            record.setUpdateUserId(UserThreadUtils.getUserId());
            record.setUpdateTime(new Date());
            num = sysApiMapper.updateByPrimaryKeySelective(record);
        }
        // 更新api需要清除角色资源缓存数据
        if (StringUtils.isNotBlank(record.getId())) {
            CacheUtils.delByPattern(RedisKeys.ROLE_URI);
        }
        return num;
    }

    @Override
    public List<SysApi> findByAll(SysApi record) {
        return sysApiMapper.findByAll(record);
    }

    @Override
    public int batchDel(List<String> idList) {
        return sysApiMapper.batchDel(idList, UserThreadUtils.getUserId());
    }

    @Override
    public List<String> getUriByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return Collections.emptyList();
        }
        return sysApiMapper.getUriByRoleId(roleId);
    }

    @Override
    public List<String> getAndCacheUriByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return Collections.emptyList();
        }
        // 先尝试从缓存获取, 缓存不存在才从数据库获取,更新或删除用户角色，需要清缓存
        return CacheUtils.cacheAndGet(RedisKeys.ROLE_URI.concat(roleId), 36000, () -> sysApiMapper.getUriByRoleId(roleId));
    }

}

















