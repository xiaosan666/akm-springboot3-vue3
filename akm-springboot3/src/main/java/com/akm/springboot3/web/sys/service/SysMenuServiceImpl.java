package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.SysMenuInfo;
import com.akm.springboot3.web.sys.entity.SysMenu;
import com.akm.springboot3.web.sys.mapper.SysMenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrUpdateSelective(SysMenu record) {
        // 校验编码是否重复
        String code = record.getCode();
        if (StringUtils.isNotBlank(code)) {
            SysMenu query = new SysMenu();
            query.setCode(code);
            List<SysMenu> list = sysMenuMapper.findByAll(query);
            boolean codeExists = list.size() > 1 || (list.size() == 1 && !list.get(0).getId().equals(record.getId()));
            if (codeExists) {
                throw new BusinessException("资源编码已经存在");
            }
        }
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(new Date());
            return sysMenuMapper.insertSelective(record);
        } else {
            record.setUpdateUserId(UserThreadUtils.getUserId());
            record.setUpdateTime(new Date());
            return sysMenuMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public List<SysMenu> findByAll(SysMenu sysMenu) {
        sysMenu.setDelFlag(0);
        return sysMenuMapper.findByAll(sysMenu);
    }

    @Override
    public int batchDel(List<String> idList) {
        return sysMenuMapper.batchDel(idList, UserThreadUtils.getUserId());
    }

    @Override
    public List<SysMenuInfo> findMenu() {
        Integer clientType = Integer.parseInt(UserThreadUtils.getClientType());
        String userId = UserThreadUtils.getUserId();
        return sysMenuMapper.findMenu(clientType, userId);
    }
}


