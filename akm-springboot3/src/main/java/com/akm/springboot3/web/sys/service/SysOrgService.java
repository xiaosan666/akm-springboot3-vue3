package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.domain.SysOrgInfo;
import com.akm.springboot3.web.sys.entity.SysOrg;

import java.util.List;

public interface SysOrgService {

    int insertSelective(SysOrg record);

    SysOrg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysOrg record);

    int batchDel(List<String> idList);

    /**
     * 根据orgId查询所有子集组织机构
     *
     * @param id 组织ID
     * @return 子集组织机构列表
     */
    List<SysOrgInfo> selectChildById(String id);

    List<SysOrg> findByAll(SysOrg sysOrg);

    int insertOrUpdate(SysOrg sysOrg);
}
