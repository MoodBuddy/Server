package moodbuddy.moodbuddy.infra.redis.service;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.repository.query.DiaryQueryRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final Random random = new Random();
    private final RedisTemplate<String, Object> redisTemplate;
    private final DiaryQueryRepository diaryQueryRepository;

    @Override
    public void deleteDiaryCaches(Long userId) {
        deleteCacheByUserIdAndCacheName(userId, "getDiaries");
//
//        for (int page = 0; page < 10; page++) {
//            String cacheKey = "getDiaries::userId:" + userId + "_page:" + page + "_size:" + 20;
//            var updatedDiaries = diaryQueryRepository.findDiariesWithPageable(userId, page, 20);
//
//            int randomTTL = 23 + random.nextInt(3);
//            redisTemplate.opsForValue().set(cacheKey, updatedDiaries, Duration.ofHours(randomTTL));
//        }
    }

    private void deleteCacheByUserIdAndCacheName(Long userId, String cacheName) {
        var pattern = cacheName + "::userId:" + userId + "*";
        var keys = redisTemplate.keys(pattern);
        Optional.ofNullable(keys).filter(keysSet -> !keysSet.isEmpty()).ifPresent(redisTemplate::delete);
    }
}
