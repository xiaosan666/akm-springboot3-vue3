package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.domain.AppCheckUpdate;
import com.akm.springboot3.web.biz.entity.BizAppVersion;
import com.akm.springboot3.web.biz.mapper.BizAppVersionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BizAppVersionServiceImpl implements BizAppVersionService {

    @Resource
    private BizAppVersionMapper bizAppVersionMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return bizAppVersionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(BizAppVersion record) {
        return bizAppVersionMapper.insertSelective(record);
    }

    @Override
    public List<BizAppVersion> findByAll(BizAppVersion bizAppVersion) {
        return bizAppVersionMapper.findByAll(bizAppVersion);
    }

    @Override
    public BizAppVersion checkUpdate(AppCheckUpdate appCheckUpdate) {
        // 获取到所有差异版本
        List<BizAppVersion> list = bizAppVersionMapper.findNewVersionRecord(appCheckUpdate);
        if (list.isEmpty()) {
            return null;
        }
        // 优先返回最新非热更新版本
        for (BizAppVersion item : list) {
            if (item.getHot() == 0) {
                return item;
            }
        }
        return list.get(0);
    }

}

