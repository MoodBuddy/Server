package moodbuddy.moodbuddy.domain.diary.repository.find;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;

public class DiaryFindRepositoryImpl implements DiaryFindRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public DiaryFindRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageCustom<DiaryResFindDTO> findDiariesWithPageable(Long userId, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResFindDTO.class,
                        diary.id,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.thumbnail
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.status.eq(DiaryStatus.PUBLISHED))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = getTotal(userId);
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());

        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    @Override
    public PageCustom<DiaryResFindDTO> findDiariesByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResFindDTO.class,
                        diary.id,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.thumbnail
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.emotion.eq(emotion))
                        .and(diary.status.eq(DiaryStatus.PUBLISHED))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = getTotal(userId);
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());

        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    @Override
    public PageCustom<DiaryResFindDTO> findDiariesByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResFindDTO.class,
                        diary.id,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.thumbnail
                ))
                .from(diary)
                .where(
                        diary.userId.eq(userId),
                        diary.status.eq(DiaryStatus.PUBLISHED),
                        diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE),
                        filterKeyWord(filterDTO.keyWord()),
                        filterYear(filterDTO.year()),
                        filterMonth(filterDTO.month()),
                        filterEmotion(filterDTO.diaryEmotion()),
                        filterSubject(filterDTO.diarySubject())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = getTotal(userId);
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());

        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private BooleanExpression filterKeyWord(String keyWord) {
        return keyWord != null && !keyWord.isEmpty()
                ? diary.title.containsIgnoreCase(keyWord).or(diary.content.containsIgnoreCase(keyWord))
                : null;
    }

    private BooleanExpression filterYear(Integer year) {
        return year != null
                ? diary.date.year().eq(year)
                : null;
    }

    private BooleanExpression filterMonth(Integer month) {
        return month != null
                ? diary.date.month().eq(month)
                : null;
    }

    private BooleanExpression filterEmotion(DiaryEmotion emotion) {
        return emotion != null
                ? diary.emotion.eq(emotion)
                : null;
    }

    private BooleanExpression filterSubject(DiarySubject subject) {
        return subject != null
                ? diary.subject.eq(subject)
                : null;
    }

    private long getTotal(Long userId) {
        return Optional.ofNullable(
                queryFactory.select(diary.count())
                        .from(diary)
                        .where(diary.userId.eq(userId)
                                .and(diary.status.eq(DiaryStatus.PUBLISHED))
                                .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                        .fetchOne()
        ).orElse(0L);
    }
}