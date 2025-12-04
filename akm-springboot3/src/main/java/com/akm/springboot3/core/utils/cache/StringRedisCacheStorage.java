package com.akm.springboot3.core.utils.cache;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 字符串缓存存储实现类
 * 基于 StringRedisTemplate 实现字符串类型的缓存操作
 *
 * @author akm
 */
public record StringRedisCacheStorage(StringRedisTemplate stringRedisTemplate) implements CacheStorage<String> {

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern.concat("*"));
    }

    @Override
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    @Override
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    @Override
    public long time() {
        RedisConnection redisConnection;
        RedisConnectionFactory connectionFactory = stringRedisTemplate.getConnectionFactory();
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
    public void setSet(String setName, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForSet().add(setName, value);
        this.expire(setName, timeout, unit);
    }

    @Override
    public Boolean isMemberBySet(String setName, String value) {
        return stringRedisTemplate.opsForSet().isMember(setName, value);
    }
}
