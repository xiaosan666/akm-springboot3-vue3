package com.akm.springboot3.web.demo.service;

import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.core.utils.DbAesUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.web.biz.service.BizOtpLogService;
import com.akm.springboot3.web.demo.entity.DemoUser;
import com.akm.springboot3.web.demo.mapper.DemoUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DemoUserServiceImpl implements DemoUserService {

    private final DemoUserMapper demoUserMapper;

    private final BizOtpLogService bizOtpLogService;

    DemoUserServiceImpl(DemoUserMapper demoUserMapper, BizOtpLogService bizOtpLogService) {
        this.demoUserMapper = demoUserMapper;
        this.bizOtpLogService = bizOtpLogService;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return demoUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DemoUser selectByPrimaryKey(String id) {
        return demoUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertOrUpdateSelective(DemoUser record) {
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            // 机密字段加密存储
            record.setIdCard(DbAesUtils.encrypt(record.getIdCard()));
            record.setCreateTime(new Date());
            return demoUserMapper.insertSelective(record);
        } else {
            if (DbAesUtils.CIPHER_TEXT.equals(record.getIdCard())) {
                record.setIdCard(selectByPrimaryKey(record.getId()).getIdCard());
            } else {
                record.setIdCard(DbAesUtils.encrypt(record.getIdCard()));
            }
            record.setUpdateTime(new Date());
            return demoUserMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public List<DemoUser> findByAll(DemoUser demoUser) {
        List<DemoUser> list = demoUserMapper.findByAll(demoUser);
        boolean decrypt = bizOtpLogService.assertOtpSecret("demo_user_view_idCard", "查看身份证号码");
        for (DemoUser record : list) {
            record.setIdCard(decrypt ? DbAesUtils.decrypt(record.getIdCard()) : DbAesUtils.CIPHER_TEXT);
        }
        return list;
    }

    @Override
    public String findUsernameById(String id) {
        return demoUserMapper.findUsernameById(id);
    }

    @Override
    public int getTotal() {
        return demoUserMapper.getTotal();
    }

    @Override
    public int batchInsert(List<DemoUser> list) {
        if (list.isEmpty()) {
            throw new BusinessException("导入数据不能为空");
        }
        List<String> names = new ArrayList<>();
        for (DemoUser user : list) {
            AssertUtils.notBlank(user.getRealname(), String.format("第%d行，姓名不允许为空", user.getRowNum()));
            AssertUtils.notBlank(user.getUsername(), String.format("第%d行，用户名不允许为空", user.getRowNum()));
            AssertUtils.notNull(user.getBirthday(), String.format("第%d行，生日不允许为空", user.getRowNum()));
            AssertUtils.notNull(user.getAge(), String.format("第%d行，年龄不允许为空", user.getRowNum()));
            AssertUtils.notNull(user.getIdCard(), String.format("第%d行，身份证号不允许为空", user.getRowNum()));
            names.add(user.getUsername());
            // 补充id
            user.setId(SnowflakeUtils.id());
            // 机密字段加密存储
            user.setIdCard(DbAesUtils.encrypt(user.getIdCard()));
        }

        StringBuilder sb = new StringBuilder();
        List<String> hasUserNames = demoUserMapper.findByUserNameList(names);
        // 遍历查询所有已存在的用户名
        if (!hasUserNames.isEmpty()) {
            for (DemoUser user : list) {
                for (String username : hasUserNames) {
                    if (username.equals(user.getUsername())) {
                        sb.append(String.format("第%d行，用户名已经存在；", user.getRowNum()));
                    }
                }
            }
            throw new BusinessException(sb.toString());
        }
        return demoUserMapper.batchInsert(list);
    }

}





