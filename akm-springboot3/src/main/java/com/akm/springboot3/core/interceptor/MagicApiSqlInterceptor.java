package com.akm.springboot3.core.interceptor;

import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.RequestEntity;
import org.ssssssss.magicapi.modules.db.BoundSql;
import org.ssssssss.magicapi.modules.db.inteceptor.SQLInterceptor;

/**
 * MagicApi数据权限SQL拦截器，原理：将sql中的关键字替换为权限相关sql语句
 *
 * @author xiaojun
 */
@Component
public class MagicApiSqlInterceptor implements SQLInterceptor {
    /**
     * 区域数据权限过滤
     */
    private static final String FILTER_QYDM = "@SQL_FILTER_QYDM";

    @Override
    public void preHandle(BoundSql boundSql, RequestEntity requestEntity) {
        String sql = boundSql.getSql();
        // 判断是否存在数据权限过滤标识
        if (sql.contains(FILTER_QYDM)) {
            String filterSql = "";
            // 管理员不进行数据权限过滤
            // todo 根据业务情况自行实现
            // if (!MagicApiThreadUtils.isAdmin()) {
            // BizOrg org = bizOrgService.getOrgByUserId(MagicApiThreadUtils.getUserId());
            // // 当用户所属组织机构的parentId等于0，则认为该用户具有最高数据权限，不进行数据权限过滤
            // if (!ORG_ROOT_ID.equals(org.getParentId())) {
            //     filterSql = " inner join biz_org org on qydm = org.id and org.level like '" + org.getLevel() + "%' ";
            // }
            // }
            boundSql.setSql(sql.replace(FILTER_QYDM, filterSql));
        }
    }
}
