package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.domain.CacheOrg;
import com.akm.springboot3.web.sys.domain.SysOrgInfo;
import com.akm.springboot3.web.sys.entity.SysOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysOrgMapper {
    int insertSelective(SysOrg record);

    SysOrg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysOrg record);

    int batchDel(@Param("idList") List<String> idList, @Param("updateUserId") String updateUserId, @Param("updateTime") Date updateTime);

    /**
     * 根据orgId查询所有子集组织机构
     *
     * @param id 组织ID
     * @return 子集组织机构列表
     */
    List<SysOrgInfo> selectChildById(String id);

    List<SysOrg> findByAll(SysOrg sysOrg);

    List<CacheOrg> findAll();

}
