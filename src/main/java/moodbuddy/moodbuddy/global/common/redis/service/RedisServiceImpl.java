package moodbuddy.moodbuddy.global.common.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void deleteDiaryCaches(Long userId) {
        deleteCacheByUserIdAndCacheName(userId, "getDiaries");
        deleteCacheByUserIdAndCacheName(userId, "getDiariesByEmotion");
        deleteCacheByUserIdAndCacheName(userId, "getDiariesByFilter");
    }

    @Override
    public void deleteBookMarkCaches(Long userId) {
        deleteCacheByUserIdAndCacheName(userId, "getBookMarks");
    }
    private void deleteCacheByUserIdAndCacheName(Long userId, String cacheName) {
        String pattern = cacheName + "::userId:" + userId + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        Optional.ofNullable(keys).filter(keysSet -> !keysSet.isEmpty()).ifPresent(redisTemplate::delete);
    }
}
