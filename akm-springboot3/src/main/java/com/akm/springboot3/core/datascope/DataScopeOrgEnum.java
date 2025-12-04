package com.akm.springboot3.core.datascope;

import lombok.Getter;

/**
 * 枚举：组织机构数据权限编码枚举值
 * 对应数据字典编码：data_scope_org
 */
@Getter
public enum DataScopeOrgEnum {


    /**
     * 全部数据权限
     */
    ALL("10", "全部数据权限"),
    /**
     * 自定义数据权限
     */
    CUSTOM("20", "自定义数据权限"),
    /**
     * 部门数据权限
     */
    DEPT("30", "部门数据权限"),
    /**
     * 部门及以下数据权限
     */
    DEPT_AND_BELOW("40", "部门及以下数据权限"),
    /**
     * 仅本人
     */
    ONLY_MYSELF("50", "仅本人");


    private final String value;

    private final String name;

    DataScopeOrgEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
