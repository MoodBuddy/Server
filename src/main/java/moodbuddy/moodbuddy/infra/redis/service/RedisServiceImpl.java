package moodbuddy.moodbuddy.infra.redis.service;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.domain.diary.repository.query.DiaryQueryRepository;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final Random random = new Random();
    private final RedisTemplate<String, Object> redisTemplate;
    private final DiaryQueryRepository diaryQueryRepository;
    private static final String DIARIES_CACHE_PREFIX = "diaries::userId:";
    private static final String DIARY_COUNT_CACHE_PREFIX = "diary_count:userId:";
    private static final int PAGE_SIZE = 20;

    @Override
    public void deleteDiaryCaches(Long userId) {
        deleteCacheByUserIdAndCacheName(userId);
        deleteCountCacheByUserId(userId);
        cacheFirstPage(userId);
    }

    private void deleteCacheByUserIdAndCacheName(Long userId) {
        var pattern = RedisServiceImpl.DIARIES_CACHE_PREFIX + userId + "*";
        var keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private void deleteCountCacheByUserId(Long userId) {
        String countCacheKey = DIARY_COUNT_CACHE_PREFIX + userId;
        redisTemplate.delete(countCacheKey);
    }

    private void cacheFirstPage(Long userId) {
        String cacheKey = DIARIES_CACHE_PREFIX + userId + "_page:0_size:" + PAGE_SIZE;
        PageCustom<DiaryResQueryDTO> firstPage = diaryQueryRepository.findDiariesWithPageable(userId, PageRequest.of(0, PAGE_SIZE));
        if (firstPage != null && !firstPage.getContent().isEmpty()) {
            int randomTTL = 23 + random.nextInt(3);
            redisTemplate.opsForValue().set(cacheKey, firstPage, Duration.ofHours(randomTTL));
        }
    }
}