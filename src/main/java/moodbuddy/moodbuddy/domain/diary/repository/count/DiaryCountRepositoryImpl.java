package moodbuddy.moodbuddy.domain.diary.repository.count;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import java.time.LocalDate;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;

public class DiaryCountRepositoryImpl implements DiaryCountRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DiaryCountRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long countByEmotionAndDateRange(final Long userId, DiaryEmotion emotion, LocalDate start, LocalDate end) {
        return queryFactory.selectFrom(diary)
                .where(diary.date.between(start, end)
                        .and(diary.emotion.eq(emotion))
                        .and(diary.userId.eq(userId))
                )
                .fetchCount();
    }

    @Override
    public long countBySubjectAndDateRange(final Long userId, DiarySubject subject, LocalDate start, LocalDate end) {
        return queryFactory.selectFrom(diary)
                .where(diary.date.between(start, end)
                        .and(diary.subject.eq(subject))
                        .and(diary.userId.eq(userId))
                )
                .fetchCount();
    }
}