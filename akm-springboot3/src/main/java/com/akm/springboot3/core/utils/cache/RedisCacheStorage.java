package com.akm.springboot3.core.utils.cache;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存存储实现类
 * 基于 RedisTemplate 实现对象类型的缓存操作
 *
 * @author akm
 */
public record RedisCacheStorage(RedisTemplate<String, Object> redisTemplate) implements CacheStorage<Object> {

    @Override
    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern.concat("*"));
    }

    @Override
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public long time() {
        RedisConnection redisConnection;
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory == null) {
            return 0;
        }
        try {
            redisConnection = connectionFactory.getConnection();
        } catch (Exception e) {
            redisConnection = connectionFactory.getClusterConnection();
        }
        try {
            Long time = redisConnection.serverCommands().time();
            return time == null ? 0 : time;
        } finally {
            redisConnection.close();
        }
    }

    @Override
    public void setSet(String setName, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForSet().add(setName, value);
        this.expire(setName, timeout, unit);
    }

    @Override
    public Boolean isMemberBySet(String setName, Object value) {
        return redisTemplate.opsForSet().isMember(setName, value);
    }
}
