package moodbuddy.moodbuddy.global.redis.service;

public interface RedisService {
    void deleteDiaryCaches(Long userId);
    void deleteBookMarkCaches(Long userId);
}
