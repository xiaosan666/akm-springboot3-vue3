package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.entity.SysTenant;
import com.akm.springboot3.web.sys.mapper.SysTenantMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xiaojun
 */
@Service
public class SysTenantServiceImpl implements SysTenantService {

    @Resource
    private SysTenantMapper sysTenantMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        String userId = UserThreadUtils.getUserId();
        String tenantId = UserThreadUtils.getTenantId();
        if (tenantId.equals(id)) {
            throw new BusinessException("不能删除自己");
        }
        return sysTenantMapper.deleteByPrimaryKey(id, userId);
    }

    @Override
    public SysTenant selectByPrimaryKey(String id) {
        return sysTenantMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysTenant> findByAll(SysTenant sysTenant) {
        sysTenant.setDelFlag(0);
        return sysTenantMapper.findByAll(sysTenant);
    }

    @Override
    public int insertOrUpdateSelective(SysTenant record) {
        // 校验编码是否重复
        String code = record.getCode();
        if (StringUtils.isNotBlank(code)) {
            SysTenant query = new SysTenant();
            query.setCode(code);
            query.setDelFlag(0);
            List<SysTenant> list = sysTenantMapper.findByAll(query);
            boolean codeExists = list.size() > 1 || (list.size() == 1 && !list.get(0).getId().equals(record.getId()));
            if (codeExists) {
                throw new BusinessException("租户编码已经存在");
            }
        }
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(new Date());
            return sysTenantMapper.insertSelective(record);
        } else {
            record.setUpdateUserId(UserThreadUtils.getUserId());
            record.setUpdateTime(new Date());
            return sysTenantMapper.updateByPrimaryKeySelective(record);
        }
    }

}
