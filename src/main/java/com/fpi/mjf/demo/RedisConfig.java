package com.fpi.mjf.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
/**
 * springboot 自带stringRedisTemplate,这里配置RedisTemplate<String, Object>
 * @author 梅纪飞
 *
 */
public class RedisConfig {
    
    @Bean
    @Qualifier("objectRedisTemplate")
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> objectRedisTemplate = new RedisTemplate<String, Object>();
        objectRedisTemplate.setKeySerializer(new StringRedisSerializer());
        objectRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        objectRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        objectRedisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        objectRedisTemplate.setEnableTransactionSupport(true);
        objectRedisTemplate.setConnectionFactory(connectionFactory);
        return objectRedisTemplate;
    }
}
