package moodbuddy.moodbuddy.infra.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    protected ObjectMapper objectMapper() {
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JavaTimeModule())
                .activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Bean
    public CacheManager getDiariesCacheManager(RedisConnectionFactory cf) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .cacheDefaults(defaultCacheConfiguration())
                .build();
    }

    @Bean
    public CacheManager getDiariesByEmotionCacheManager(RedisConnectionFactory cf) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .cacheDefaults(cacheConfig)
                .build();
    }

    @Bean
    public CacheManager getDiariesByFilterCacheManager(RedisConnectionFactory cf) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .cacheDefaults(cacheConfig)
                .build();
    }

    private RedisCacheConfiguration defaultCacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper())))
                .entryTtl(Duration.ofHours(24));
    }
}