package com.akm.springboot3.core.enums;

import lombok.Getter;

/**
 * 对接系统分类
 * 客户端类型
 *
 * @author xiaojun
 *
 */
@Getter
public enum ClientTypeEnum {
    /**
     * web端
     */
    WEB("1", "WEB"),

    /**
     * 移动端
     */
    APP("2", "APP"),

    /**
     * 其他类型
     */
    OTHER("9", "OTHER");

    private final String value;

    private final String name;

    ClientTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
