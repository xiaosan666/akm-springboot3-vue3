package com.akm.springboot3.core.interceptor.mybatis;


import com.akm.springboot3.core.datascope.DataScopeAspect;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 数据权限连接器
 *
 * @author xiaojun
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
    }
)
@Slf4j
public class MyBatisSqlInterceptor implements Interceptor {

    public static final String DATA_SCOPE_ORG = "@DATA_SCOPE_ORG";

    private static String getNewSql(String sql) {
        // 从线程中获取权限sql
        String dataScopeOrgSql = ThreadContext.get(DataScopeAspect.DATA_SCOPE_ORG_SQL);
        // sql中存在组织机构权限关键字，则使用权限sql直接替换关键字，适用于复杂sql，不能准确计算权限sql插入位置时
        if (sql.contains(DATA_SCOPE_ORG)) {
            return sql.replace(DATA_SCOPE_ORG, StringUtils.isNotBlank(dataScopeOrgSql) ? dataScopeOrgSql : "");
        }
        // 没有权限sql则认为无需过滤权限或拥有"全部数据权限"
        if (StringUtils.isBlank(dataScopeOrgSql)) {
            return sql;
        }
        // 查找权限sql表别名，用于计算插入的位置
        String tableAlias = getTableAlias(dataScopeOrgSql);
        // 没有表别名，则认为是简单sql，直接在sql最后面加上条件
        if (tableAlias == null) {
            return sqlAddDataScopeSql(sql, dataScopeOrgSql);
        }
        // 获取sql中FROM位置，注：在复杂sql中这里判断可能不准确，请在sql中手动添加权限关键字：@DATA_SCOPE_ORG
        int indexFrom = sql.toUpperCase().lastIndexOf("FROM");
        // 获取表别名位置，从indexFrom后面开始判断
        int aliasIndex = sql.substring(indexFrom).toUpperCase().indexOf(tableAlias.toUpperCase()) + indexFrom;
        if (aliasIndex == -1) {
            log.error("数据权限配置中有表别名，sql中缺少表别名：{}", tableAlias);
            throw new BusinessException("服务端出错，缺少表别名：" + tableAlias);
        }
        // sql后半部分，从别名开始截取sql
        String secondHalf = sql.substring(aliasIndex);
        // 判断sql后半部分有无括号
        int bracketIndex = secondHalf.indexOf(')');
        // sql没有括号，则直接在sql后面添加数据权限sql
        if (bracketIndex == -1) {
            return sqlAddDataScopeSql(sql, dataScopeOrgSql);
        }
        // sql中有括号，则需要在括号内加数据权限sql
        // 括号前半部门sql
        String firstSql = sql.substring(0, aliasIndex + bracketIndex);
        // 括号后半部门sql
        String secondSql = sql.substring(aliasIndex + bracketIndex);
        return sqlAddDataScopeSql(firstSql, dataScopeOrgSql) + secondSql;
    }

    /**
     * 拼接权限sql，判断sql中有无where语句添加
     */
    private static String sqlAddDataScopeSql(String sql, String dataScopeSql) {
        int index = sql.toUpperCase().lastIndexOf("WHERE");
        if (index == -1) {
            return sql + " WHERE " + dataScopeSql;
        }
        return sql.substring(0, index + 6) + dataScopeSql + " AND " + sql.substring(index + 6);
    }

    /**
     * 获取权限sql表别名
     */
    private static String getTableAlias(String dataScopeOrgSql) {
        // 判断权限sql中是否有表别名
        boolean matches = dataScopeOrgSql.matches(".+\\.\\w+.+(=|in).+");
        if (matches) {
            // 截取表别名
            return dataScopeOrgSql.substring(0, dataScopeOrgSql.indexOf('.'));
        }
        return null;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        String sql = boundSql.getSql();

        // resetSqlInvocation(invocation, getNewSql(sql));
        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, getNewSql(sql));

        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
