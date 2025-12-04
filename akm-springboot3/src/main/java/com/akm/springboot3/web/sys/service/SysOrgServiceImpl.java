package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.config.CacheInitializer;
import com.akm.springboot3.core.datascope.DataScopeOrg;
import com.akm.springboot3.core.datascope.DataScopeService;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.SysOrgInfo;
import com.akm.springboot3.web.sys.entity.SysOrg;
import com.akm.springboot3.web.sys.mapper.SysOrgMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysOrgServiceImpl implements SysOrgService {

    private final SysOrgMapper sysOrgMapper;

    private final CacheInitializer cacheInitializer;

    private final DataScopeService dataScopeService;

    SysOrgServiceImpl(SysOrgMapper sysOrgMapper, CacheInitializer cacheInitializer, DataScopeService dataScopeService) {
        this.sysOrgMapper = sysOrgMapper;
        this.cacheInitializer = cacheInitializer;
        this.dataScopeService = dataScopeService;
    }

    @Override
    public int insertSelective(SysOrg record) {
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
        }
        record.setCreateTime(new Date());
        record.setCreateUserId(UserThreadUtils.getUserId());
        return sysOrgMapper.insertSelective(record);
    }

    @Override
    public SysOrg selectByPrimaryKey(String id) {
        // 断言数据权限
        dataScopeService.assertOrg(id);
        return sysOrgMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysOrg record) {
        record.setUpdateTime(new Date());
        record.setUpdateUserId(UserThreadUtils.getUserId());
        return sysOrgMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public int batchDel(List<String> idList) {
        return sysOrgMapper.batchDel(idList, UserThreadUtils.getUserId(), new Date());
    }

    @Override
    public List<SysOrgInfo> selectChildById(String id) {
        // 断言数据权限
        dataScopeService.assertOrg(id);
        return sysOrgMapper.selectChildById(id);
    }

    @Override
    @DataScopeOrg(orgFiledAlias = "id", userFileAlias = "")
    public List<SysOrg> findByAll(SysOrg sysOrg) {
        return sysOrgMapper.findByAll(sysOrg);
    }

    @Override
    public int insertOrUpdate(SysOrg sysOrg) {
        String orgId = StringUtils.isBlank(sysOrg.getId()) ? SnowflakeUtils.id() : sysOrg.getId();

        String parentId = sysOrg.getParentId();
        AssertUtils.notBlank(parentId, "父级组织机构不能为空");

        // 找出父级组织信息（新增操作使用sysOrgMapper进行查询不经过数据权限判断）
        SysOrg parentOrg = sysOrgMapper.selectByPrimaryKey(parentId);
        if (parentOrg == null) {
            sysOrg.setOrgFullName(sysOrg.getName());
            sysOrg.setOrgFullId(orgId);
        } else {
            // 拼接orgFullName
            sysOrg.setOrgFullName(parentOrg.getOrgFullName() + "/" + sysOrg.getName());
            // 拼接orgFullId
            sysOrg.setOrgFullId(parentOrg.getOrgFullId() + "," + orgId);
        }

        int i;
        if (StringUtils.isBlank(sysOrg.getId())) {
            sysOrg.setId(orgId);
            i = insertSelective(sysOrg);
        } else {
            i = updateByPrimaryKeySelective(sysOrg);
        }
        // 更新组织机构缓存
        cacheInitializer.cacheOrgData();
        return i;
    }
}
