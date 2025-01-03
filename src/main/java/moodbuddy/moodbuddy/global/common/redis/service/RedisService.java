package moodbuddy.moodbuddy.global.common.redis.service;

public interface RedisService {
    void deleteDiaryCaches(Long userId);
    void deleteBookMarkCaches(Long userId);
}
