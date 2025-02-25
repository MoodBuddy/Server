package moodbuddy.moodbuddy.infra.redis.service;

public interface RedisService {
    void deleteDiaryCaches(Long userId);
}
