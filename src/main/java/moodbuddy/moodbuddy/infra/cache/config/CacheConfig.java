package moodbuddy.moodbuddy.infra.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("diaries");
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(10000)
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .recordStats()
        );
        return cacheManager;
    }
}