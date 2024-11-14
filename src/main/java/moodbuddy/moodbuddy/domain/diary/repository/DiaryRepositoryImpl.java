package moodbuddy.moodbuddy.domain.diary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.QDiary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import java.time.LocalDate;
import java.util.List;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;
import static moodbuddy.moodbuddy.domain.diary.domain.image.QDiaryImage.diaryImage;


public class DiaryRepositoryImpl implements DiaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public DiaryResDetailDTO findOneByDiaryId(Long diaryId) {
        DiaryResDetailDTO diaryResDetailDTO = queryFactory.select(Projections.constructor(DiaryResDetailDTO.class,
                        diary.diaryId,
                        diary.userId,
                        diary.diaryTitle,
                        diary.diaryDate,
                        diary.diaryContent,
                        diary.diaryWeather,
                        diary.diaryEmotion,
                        diary.diaryStatus,
                        diary.diarySummary,
                        diary.diarySubject,
                        diary.diaryBookMarkCheck,
                        diary.diaryFont,
                        diary.diaryFontSize,
                        diary.moodBuddyStatus
                ))
                .from(diary)
                .where(diary.diaryId.eq(diaryId).and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetchOne();

        List<String> diaryImgList = queryFactory.select(diaryImage.diaryImgURL)
                .from(diaryImage)
                .where(diaryImage.diary.diaryId.eq(diaryId))
                .fetch();

        diaryResDetailDTO.setDiaryImgList(diaryImgList);

        return diaryResDetailDTO;
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