package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.entity.BizOtpLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BizOtpLogMapper {
    int insert(BizOtpLog record);

    BizOtpLog selectOneByUserId(@Param("userId") String userId);
}
