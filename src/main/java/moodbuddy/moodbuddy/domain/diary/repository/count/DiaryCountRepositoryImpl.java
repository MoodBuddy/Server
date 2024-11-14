package moodbuddy.moodbuddy.domain.diary.repository.count;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.QDiary;
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
    public long countByEmotionAndDateRange(DiaryEmotion emotion, LocalDate start, LocalDate end) {
        QDiary qDiary = diary;
        return queryFactory.selectFrom(qDiary)
                .where(qDiary.diaryDate.between(start, end)
                        .and(qDiary.diaryEmotion.eq(emotion)))
                .fetchCount();
    }

    @Override
    public long countBySubjectAndDateRange(DiarySubject subject, LocalDate start, LocalDate end) {
        QDiary qDiary = diary;
        return queryFactory.selectFrom(qDiary)
                .where(qDiary.diaryDate.between(start, end)
                        .and(qDiary.diarySubject.eq(subject)))
                .fetchCount();
    }
}
