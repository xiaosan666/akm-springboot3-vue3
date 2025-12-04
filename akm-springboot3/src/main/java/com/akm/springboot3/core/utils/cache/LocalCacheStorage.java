package com.akm.springboot3.core.utils.cache;

import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 本地缓存存储实现类
 * 基于 ConcurrentHashMap 实现本地内存缓存
 * 注意：生产环境建议使用 Redis 缓存
 *
 * @param <V> 缓存值类型
 * @author akm
 */
@Getter
public class LocalCacheStorage<V> implements CacheStorage<V> {

    private final ConcurrentHashMap<String, CacheWrapper<V>> cache = new ConcurrentHashMap<>();

    /**
     * 每10秒清理本地缓存过期的key，生产环境务必使用redis
     */
    public LocalCacheStorage() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cache.keySet().forEach(key -> {
                    if (cache.get(key).isExpired()) {
                        cache.remove(key);
                    }
                });
            }
        }, 0, 10000);
    }

    @Override
    public V get(String key) {
        CacheWrapper<V> wrapper = cache.get(key);
        if (wrapper == null) {
            return null;
        }
        if (wrapper.isExpired()) {
            cache.remove(key);
            return null;
        }
        return wrapper.getCacheObject();
    }

    @Override
    public void delete(String key) {
        cache.remove(key);
    }

    @Override
    public boolean hasKey(String key) {
        return cache.containsKey(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        Pattern pattern1 = Pattern.compile(pattern);
        return cache.keySet().stream()
            .filter(pattern1.asPredicate())
            .collect(Collectors.toSet());
    }

    @Override
    public void delete(Collection<String> keys) {
        for (String key : keys) {
            cache.remove(key);
        }
    }

    @Override
    public void set(String key, V value) {
        CacheWrapper<V> wrapper = new CacheWrapper<>(value);
        cache.put(key, wrapper);
    }

    @Override
    public void set(String key, V value, long timeout, TimeUnit unit) {
        CacheWrapper<V> wrapper;
        if (timeout > 0 && unit != null) {
            long expire = unit.toSeconds(timeout);
            wrapper = new CacheWrapper<>(value, expire);
        } else {
            wrapper = new CacheWrapper<>(value);
        }
        cache.put(key, wrapper);
    }

    @Override
    public boolean setIfAbsent(String key, V value) {
        CacheWrapper<V> wrapper = new CacheWrapper<>(value);
        return cache.putIfAbsent(key, wrapper) == null;
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        CacheWrapper<V> wrapper = cache.get(key);
        if (wrapper == null || wrapper.isExpired()) {
            return false;
        }
        long expire = unit.toSeconds(timeout);
        wrapper.setExpire(expire);
        return true;
    }

    @Override
    public Long getExpire(String key) {
        CacheWrapper<V> wrapper = cache.get(key);
        if (wrapper == null) {
            return -2L;
        }
        return wrapper.getExpire();
    }

    @Override
    public long time() {
        return System.currentTimeMillis();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setSet(String setName, V value, long timeout, TimeUnit unit) {
        Object o = this.get(setName);
        HashSet<V> set = new HashSet<>();
        if (o == null) {
            set.add(value);
            this.set(setName, (V) set, timeout, unit);
        } else {
            set = (HashSet<V>) o;
            set.add(value);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean isMemberBySet(String setName, V value) {
        Object o = this.get(setName);
        if (o == null) {
            return false;
        } else {
            HashSet<V> set = (HashSet<V>) o;
            return set.contains(value);
        }
    }
}
