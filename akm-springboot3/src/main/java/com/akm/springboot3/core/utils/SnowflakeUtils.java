package com.akm.springboot3.core.utils;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 雪花算法id生成工具类
 *
 * @author xiaojun
 *
 */
@Component
public class SnowflakeUtils {
    /**
     * 雪花算法终端ID，最大支持机器节点数0~31
     */
    private static long workerId;
    private static volatile cn.hutool.core.lang.Snowflake snowflake;

    private SnowflakeUtils() {
    }

    public static String id() {
        return Long.toString(nextId());
    }

    public static Long nextId() {
        if (snowflake == null) {
            synchronized (cn.hutool.core.lang.Snowflake.class) {
                // 雪花算法数据中心ID，最大支持机器节点数0~31
                int datacenterId = RandomUtil.randomInt(0, 31);
                snowflake = new cn.hutool.core.lang.Snowflake(workerId, datacenterId, true);
            }
        }
        return snowflake.nextId();
    }

    @Value("${snowflake.worker-id}")
    public synchronized void setWorkerId(long val) {
        workerId = val;
    }

}
