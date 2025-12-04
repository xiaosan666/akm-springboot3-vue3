package com.akm.springboot3.core.datascope;

import cn.hutool.core.collection.CollUtil;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.ThreadContext;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 切面：数据权限拦截器
 *
 * @author xiaojun
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class DataScopeAspect {

    public static final String DATA_SCOPE_ORG_SQL = "DATA_SCOPE_ORG_SQL";

    @Resource
    private DataScopeService dataScopeService;

    /**
     * 组织机构数据权限过滤注解切面
     *
     * @param point
     * @param myDataAuthScope
     */
    @Before("@annotation(myDataAuthScope)")
    public void doBefore(JoinPoint point, DataScopeOrg myDataAuthScope) {
        // 用户角色列表
        List<SysRoleBaseInfo> roleList = UserThreadUtils.getRoleList();
        // 组织机构权限编码，对应枚举类：DataScopeOrgEnum
        List<String> orgCodes = CollUtil.map(roleList, SysRoleBaseInfo::getDataScopeOrg, true);
        // 有DataScopeOrg注解，用户没有角色/权限编码，则拒绝访问
        if (orgCodes == null || orgCodes.isEmpty()) {
            log.warn("没有访问权限，登录用户角色编码为空，用户ID：{}", UserThreadUtils.getUserId());
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }

        // 组织机构权限编码包含"全部数据权限"
        if (orgCodes.contains(DataScopeOrgEnum.ALL.getValue())) {
            return;
        }

        String orgFiledAlias = myDataAuthScope.orgFiledAlias();
        String userFileAlias = myDataAuthScope.userFileAlias();

        // "仅本人"数据权限
        if (orgCodes.size() == 1 && orgCodes.get(0).equals(DataScopeOrgEnum.ONLY_MYSELF.getValue()) && StringUtils.isNotBlank(userFileAlias)) {
            // 拼sql，形如：t1.user_id = '111'
            String dataScopeOrgSql = String.format(" %s = '%s' ", userFileAlias, UserThreadUtils.getUserId());
            ThreadContext.set(DATA_SCOPE_ORG_SQL, dataScopeOrgSql);
            return;
        }

        // 用户组织机构权限集合
        HashSet<String> orgIdSet = dataScopeService.getOrgIdListByDataScopeOrg(roleList, UserThreadUtils.getOrgId());

        String dataScopeOrgSql;
        if (orgIdSet.size() == 1) {
            // 拼sql，形如：t1.org_id = '111'
            dataScopeOrgSql = String.format(" %s = '%s' ", orgFiledAlias, CollUtil.get(orgIdSet, 0));
        } else {
            // 拼sql，形如：t1.org_id in ('111', '222')
            String orgStr = orgIdSet.stream().map(orgId -> "'" + orgId + "'").collect(Collectors.joining(","));
            dataScopeOrgSql = String.format(" %s in (%s) ", orgFiledAlias, orgStr);
        }
        ThreadContext.set(DATA_SCOPE_ORG_SQL, dataScopeOrgSql);
    }

    @After("@annotation(myDataAuthScope)")
    public void doAfter(DataScopeOrg myDataAuthScope) {
        ThreadContext.remove(DATA_SCOPE_ORG_SQL);
    }

}
