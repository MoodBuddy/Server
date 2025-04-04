package moodbuddy.moodbuddy.domain.diary.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.request.query.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiaryQuery.diaryQuery;

public class DiaryQueryRepositoryImpl implements DiaryQueryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_PREFIX = "diary_count:userId:";
    public DiaryQueryRepositoryImpl(EntityManager em, RedisTemplate<String, Object> redisTemplate) {
        this.queryFactory = new JPAQueryFactory(em);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public PageCustom<DiaryResQueryDTO> findDiariesWithPageable(Long userId, boolean isAscending, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResQueryDTO.class,
                        diaryQuery.diaryId,
                        diaryQuery.title,
                        diaryQuery.date,
                        diaryQuery.content,
                        diaryQuery.thumbnail
                ))
                .from(diaryQuery)
                .where(
                        diaryQuery.userId.eq(userId),
                        diaryQuery.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)
                )
                .orderBy(isAscending ? diaryQuery.date.asc() : diaryQuery.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = pageable.getPageNumber() == 0 ? getCachedTotal(userId) : -1;
        int totalPages = total >= 0 ? (int) Math.ceil((double) total / pageable.getPageSize()) : -1;
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private long getCachedTotal(Long userId) {
        String cacheKey = CACHE_PREFIX + userId;
        String cachedValue = (String) redisTemplate.opsForValue().get(cacheKey);
        if (cachedValue != null) {
            return Long.parseLong(cachedValue);
        }
        long count = getTotal(userId, null, null, null, null, null);
        int randomTTL = ThreadLocalRandom.current().nextInt(600, 1800);
        redisTemplate.opsForValue().set(cacheKey, String.valueOf(count), Duration.ofSeconds(randomTTL));
        return count;
    }


    @Override
    public PageCustom<DiaryResQueryDTO> findDiariesByEmotionWithPageable(Long userId, boolean isAscending, DiaryEmotion emotion, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResQueryDTO.class,
                        diaryQuery.diaryId,
                        diaryQuery.title,
                        diaryQuery.date,
                        diaryQuery.content,
                        diaryQuery.thumbnail
                ))
                .from(diaryQuery)
                .where(
                        diaryQuery.userId.eq(userId),
                        diaryQuery.emotion.eq(emotion),
                        diaryQuery.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)
                )
                .orderBy(isAscending ? diaryQuery.date.asc() : diaryQuery.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = pageable.getPageNumber() == 0 ? getTotal(userId, emotion, null, null, null, null) : -1;
        int totalPages = total >= 0 ? (int) Math.ceil((double) total / pageable.getPageSize()) : -1;
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    @Override
    public PageCustom<DiaryResQueryDTO> findDiariesByFilterWithPageable(Long userId, boolean isAscending, DiaryReqFilterDTO filterDTO, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResQueryDTO.class,
                        diaryQuery.diaryId,
                        diaryQuery.title,
                        diaryQuery.date,
                        diaryQuery.content,
                        diaryQuery.thumbnail
                ))
                .from(diaryQuery)
                .where(
                        diaryQuery.userId.eq(userId),
                        diaryQuery.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE),
                        filterKeyWord(filterDTO.keyWord()),
                        filterYear(filterDTO.year()),
                        filterMonth(filterDTO.month()),
                        filterEmotion(filterDTO.diaryEmotion()),
                        filterSubject(filterDTO.diarySubject())
                )
                .orderBy(isAscending ? diaryQuery.date.asc() : diaryQuery.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = pageable.getPageNumber() == 0 ? getTotal(userId, filterDTO.diaryEmotion(), filterDTO.diarySubject(), filterDTO.year(), filterDTO.month(), filterDTO.keyWord()) : -1;
        int totalPages = total >= 0 ? (int) Math.ceil((double) total / pageable.getPageSize()) : -1;
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private BooleanExpression filterKeyWord(String keyWord) {
        return keyWord != null && !keyWord.isEmpty()
                ? diaryQuery.title.containsIgnoreCase(keyWord).or(diaryQuery.content.containsIgnoreCase(keyWord))
                : null;
    }

    private BooleanExpression filterYear(Integer year) {
        return year != null
                ? diaryQuery.date.year().eq(year)
                : null;
    }

    private BooleanExpression filterMonth(Integer month) {
        return month != null
                ? diaryQuery.date.month().eq(month)
                : null;
    }

    private BooleanExpression filterEmotion(DiaryEmotion emotion) {
        return emotion != null
                ? diaryQuery.emotion.eq(emotion)
                : null;
    }

    private BooleanExpression filterSubject(DiarySubject subject) {
        return subject != null
                ? diaryQuery.subject.eq(subject)
                : null;
    }

    private long getTotal(Long userId, DiaryEmotion emotion, DiarySubject subject, Integer year, Integer month, String keyWord) {
        return Optional.ofNullable(
                queryFactory.select(diaryQuery.count())
                        .from(diaryQuery)
                        .where(
                                diaryQuery.userId.eq(userId),
                                diaryQuery.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE),
                                filterEmotion(emotion),
                                filterSubject(subject),
                                filterYear(year),
                                filterMonth(month),
                                filterKeyWord(keyWord)
                        )
                        .fetchOne()
        ).orElse(0L);
    }
}