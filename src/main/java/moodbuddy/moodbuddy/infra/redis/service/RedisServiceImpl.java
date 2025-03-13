package moodbuddy.moodbuddy.infra.redis.service;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.domain.diary.repository.query.DiaryQueryRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final DiaryQueryRepository diaryQueryRepository;

    @Override
    public void deleteDiaryCaches(Long userId) {
        deleteCacheByUserIdAndCacheName(userId, "getDiary");
        deleteCacheByUserIdAndCacheName(userId, "getDiaries");
        deleteCacheByUserIdAndCacheName(userId, "getDiariesByEmotion");
        deleteCacheByUserIdAndCacheName(userId, "getDiariesByFilter");
    }

    private void deleteCacheByUserIdAndCacheName(Long userId, String cacheName) {
        String pattern = cacheName + "::userId:" + userId + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        Optional.ofNullable(keys).filter(keysSet -> !keysSet.isEmpty()).ifPresent(redisTemplate::delete);
    }

    private void updateDiaryCache(Long userId) {
        String cacheKey = "getDiaries::userId:" + userId;
        redisTemplate.delete(cacheKey);

        List<DiaryResQueryDTO> updatedDiaries = diaryQueryRepository.find(userId);
        int randomTTL = 23 + random.nextInt(3);
        redisTemplate.opsForValue().set(cacheKey, updatedDiaries, Duration.ofHours(randomTTL));
    }
}
