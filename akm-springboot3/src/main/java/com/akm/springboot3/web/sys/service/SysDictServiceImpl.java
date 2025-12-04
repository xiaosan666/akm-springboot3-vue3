package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.SysDictInfo;
import com.akm.springboot3.web.sys.entity.SysDict;
import com.akm.springboot3.web.sys.mapper.SysDictMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public int insertOrUpdateSelective(SysDict record) {
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(new Date());
            return sysDictMapper.insertSelective(record);
        } else {
            record.setUpdateUserId(UserThreadUtils.getUserId());
            record.setUpdateTime(new Date());
            return sysDictMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public List<SysDict> findByAll(SysDict record) {
        return sysDictMapper.findByAll(record);
    }

    @Override
    public int batchDel(List<String> idList) {
        if (idList.isEmpty()) {
            return 0;
        }
        return sysDictMapper.batchDel(idList, UserThreadUtils.getUserId());
    }

    @Override
    public List<SysDictInfo> findByTypes(List<String> typeList) {
        return sysDictMapper.findByTypes(typeList);
    }

}











