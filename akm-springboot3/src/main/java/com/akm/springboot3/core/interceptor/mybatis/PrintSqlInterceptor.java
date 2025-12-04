package com.akm.springboot3.core.interceptor.mybatis;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;


/**
 * MyBatis拦截器打印不带问号的完整sql语句
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
        Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
        Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
        Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Slf4j
@Getter
public class PrintSqlInterceptor implements Interceptor {
    /**
     * 不打印sql的方法
     */
    private final String[] excludeSqlIds = new String[]{
        "com.akm.springboot3.web.sys.mapper.SysLogMapper.insert"
    };

    /**
     * 慢 sql 时长
     */
    private Long slowSqlDuration;


    public PrintSqlInterceptor(Long slowSqlDuration) {
        this.slowSqlDuration = slowSqlDuration;
    }

    public PrintSqlInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String sqlId = null;
        String sql = null;
        try {
            Object[] args = invocation.getArgs();
            // 获取xml中的一个select/update/insert/delete节点，是一条SQL语句
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            // 获取到节点的id,即sql语句的id
            sqlId = mappedStatement.getId();
            // 跳过白名单
            if (ArrayUtil.contains(excludeSqlIds, sqlId)) {
                return invocation.proceed();
            }
            Object parameter = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            BoundSql boundSql;
            //由于逻辑关系，只会进入一次
            if (args.length == 2 || args.length == 4) {
                boundSql = mappedStatement.getBoundSql(parameter);
            } else {
                //6 个参数时
                boundSql = (BoundSql) args[5];
            }
            Configuration configuration = mappedStatement.getConfiguration();
            sql = getSql(configuration, boundSql, sqlId);
            log.info(" {} : {} ", sqlId, sql);
        } catch (Exception e) {
            log.error("print sql fail ,{}:{}", sqlId, sql, e);
        }
        //跳过慢sql打印
        if (getSlowSqlDuration() == null) {
            return invocation.proceed();
        }
        //慢 sql 打印
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        // 防止慢查询日志打印抛出异常影响到正常业务逻辑
        try {
            long end = System.currentTimeMillis();
            long diff = end - start;
            if (diff >= getSlowSqlDuration()) {
                if (log.isWarnEnabled()) {
                    log.warn("execute sql duration > {}ms,diff = {}ms,{}:{}", getSlowSqlDuration(), diff, sqlId, sql);
                }
            }
        } catch (Exception e) {
            log.error("print slow sql fail ,{}:{}", sqlId, sql, e);
        }
        return result;
    }

    /**
     * 获取到最终的sql语句
     */
    public String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
        return showSql(configuration, boundSql);
    }

    /**
     * 将 obj 转 String 对象
     * 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     *
     * @param obj 参数值
     */
    private String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format((Date) obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    /**
     * 进行？的替换
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                    Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                            Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                            Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "LOST_PARAM");
                    }
                }
            }
        }
        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
