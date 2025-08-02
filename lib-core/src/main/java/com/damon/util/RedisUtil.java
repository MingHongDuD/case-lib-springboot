package com.damon.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务类，封装常用的 Redis 操作方法。
 * 提供键值对、哈希、列表、集合等数据结构的便捷操作。
 */
@Service
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;


    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 存储键值对数据到 Redis。
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒），若 <= 0 则不过期
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value);
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取键对应的值。
     *
     * @param key 键
     * @return 值，若键不存在返回 null
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除指定键。
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 存储哈希数据到 Redis。
     *
     * @param key     哈希键
     * @param hashKey 哈希字段
     * @param value   值
     */
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取哈希数据。
     *
     * @param key     哈希键
     * @param hashKey 哈希字段
     * @return 值，若不存在返回 null
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 向列表左侧推入元素。
     *
     * @param key   列表键
     * @param value 值
     * @return 列表长度
     */
    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 从列表右侧弹出元素。
     *
     * @param key 列表键
     * @return 弹出的值，若列表为空返回 null
     */
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 向集合添加元素。
     *
     * @param key   集合键
     * @param value 值
     * @return 添加的元素数量
     */
    public Long sAdd(String key, Object value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取集合所有元素。
     *
     * @param key 集合键
     * @return 集合中的所有元素
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 设置键的过期时间。
     *
     * @param key     键
     * @param timeout 过期时间（秒）
     * @return 是否设置成功
     */
    public boolean expire(String key, long timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TimeUnit.SECONDS));
    }
}
