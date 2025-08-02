package com.damon.config.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类，提供 RedisTemplate 的自定义配置。
 *
 * @author damon du/minghongdud
 */
@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate，用于操作 Redis 数据。
     * - 使用 StringRedisSerializer 序列化键，确保键以字符串形式存储。
     * - 使用 GenericJackson2JsonRedisSerializer 序列化值，支持复杂对象的 JSON 序列化。
     * - 启用默认序列化器，防止未配置序列化器时的异常行为。
     *
     * @param connectionFactory Redis 连接工厂，自动注入 Spring Boot 配置的连接工厂
     * @return 自定义配置的 RedisTemplate 实例
     */
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 配置键的序列化方式为字符串
        StringRedisSerializer serializer = new StringRedisSerializer();
        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);

        // 配置值的序列化方式为 JSON
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 启用默认序列化器
        template.setDefaultSerializer(jackson2JsonRedisSerializer);

        // 初始化 RedisTemplate
        template.afterPropertiesSet();
        return template;
    }
}
