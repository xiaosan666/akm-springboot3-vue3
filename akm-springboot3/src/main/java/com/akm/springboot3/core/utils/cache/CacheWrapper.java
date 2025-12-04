package com.akm.springboot3.core.utils.cache;

import lombok.Getter;

/**
 * 缓存包装类
 * 封装缓存对象及其过期时间信息
 *
 * @param <V> 缓存值类型
 * @author akm
 */
@Getter
class CacheWrapper<V> {
    /**
     * 缓存数据
     */
    private final V cacheObject;

    /**
     * 最后加载时间
     */
    private long lastLoadTime;

    /**
     * 缓存时长(秒)
     */
    private long expire;

    CacheWrapper(V cacheObject) {
        this(cacheObject, -1);
    }

    CacheWrapper(V cacheObject, long expire) {
        this.cacheObject = cacheObject;
        this.lastLoadTime = System.currentTimeMillis();
        this.expire = expire;
    }

    boolean isExpired() {
        return expire > 0 && ((System.currentTimeMillis() - lastLoadTime) > (expire * 1000));
    }

    V getCacheObject() {
        lastLoadTime = System.currentTimeMillis();
        return cacheObject;
    }

    long getExpire() {
        long temp = expire - ((System.currentTimeMillis() - lastLoadTime) / 1000);
        if (temp < 0) {
            return -1;
        }
        return temp;
    }

    void setExpire(long expire) {
        lastLoadTime = System.currentTimeMillis();
        this.expire = expire;
    }
}
