package biz.digitalhouse.integration.v3.configs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kirill.arbuzov
 * @created 08/02/2018
 */
//@RefreshScope
@Configuration
public class RedisConfig {

    @Bean
    @Autowired
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        jsonRedisSerializer.setObjectMapper(new ObjectMapper()
//                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
//                .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL));
//        redisTemplate.setValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

}
