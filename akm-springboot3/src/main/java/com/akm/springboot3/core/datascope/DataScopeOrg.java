package com.akm.springboot3.core.datascope;


import java.lang.annotation.*;

/**
 * 注解：组织机构数据权限过滤注解
 *
 * @author xiaojun
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScopeOrg {

    /**
     * 组织机构字段
     * 当数据权限范围为部门及一下或部门，则使用该字段进行权限过滤，权限sql拼接为 org_id in（'1'，'2'）
     * 当业务sql存在表别名则需要添加别名，例1：表别名：t1，字段：org_id，则该属性值需要配置为 t1.org_id 例2：表别名：org，字段：id，则该属性值需要配置为 org.id
     */
    String orgFiledAlias() default "org_id";

    /**
     * 用户id字段
     * 注1：当数据权限范围为仅自己，则使用该字段进行权限过滤，权限sql拼接为 create_user_id = '1'
     * 例1：表别名：t1，字段：create_user_id，则该属性值需要配置为 t1.create_user_id 例2：表别名：user，字段：id，则该属性值需要配置为 user.id
     * 注2：当值配置为空字符串：""，则不拼接"仅自己"权限sql
     */
    String userFileAlias() default "create_user_id";

}
