package com.akm.springboot3.core.interceptor.mybatis;

import cn.hutool.core.util.ReflectUtil;
import com.akm.springboot3.core.utils.UserThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;


/**
 * 未启用
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class AuditFieldInterceptor implements Interceptor {
    public AuditFieldInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws IllegalAccessException, SQLException, InvocationTargetException {
        Object[] args = invocation.getArgs();
        if (invocation.getTarget() instanceof Executor executor && args.length == 2) {
            // 获取第一个参数
            final MappedStatement ms = (MappedStatement) args[0];
            final Object paramObj = invocation.getArgs()[1];
            Class<?> aClass = paramObj.getClass();
            if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
                Field[] fields = ReflectUtil.getFields(aClass);
                for (Field field : fields) {
                    String name = field.getName();
                    if ("id".equals(name)) {
                        field.setAccessible(true);
                        Object o = field.get(paramObj);
                        if (o instanceof String id) {
                            log.debug(id);
                        }
                    }
                    if ("createUserId".equals(name) || "updateUserId".equals(name)) {
                        this.setFieldUserId(field, paramObj);
                    }
                    if ("createTime".equals(name) || "updateTime".equals(name)) {
                        this.setFieldDate(field, paramObj);
                    }
                }
                return executor.update(ms, paramObj);
            }
            if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
                Field[] fields = ReflectUtil.getFields(aClass);
                for (Field field : fields) {
                    String name = field.getName();
                    if ("updateUserId".equals(name)) {
                        this.setFieldUserId(field, paramObj);
                    }
                    if ("updateTime".equals(name)) {
                        this.setFieldDate(field, paramObj);
                    }
                }
                return executor.update(ms, paramObj);
            }
        }
        // 继续执行原有的 SQL 语句
        return invocation.proceed();
    }

    private void setFieldDate(Field field, Object paramObj) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(paramObj, new Date());
    }

    private void setFieldUserId(Field field, Object paramObj) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(paramObj, UserThreadUtils.getUserId());
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可选的配置属性
    }
}
