package moodbuddy.moodbuddy.domain.diary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;

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
        List<String> diaryImages = queryFactory
                .select(diaryImage.imageUrl)
                .from(diaryImage)
                .where(diaryImage.diaryId.eq(diaryId).and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();

       return queryFactory.select(Projections.constructor(DiaryResDetailDTO.class,
                        diary.id,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.weather,
                        diary.emotion,
                        diary.bookMark,
                        diary.font,
                        diary.fontSize
                ))
                .from(diary)
                .where(diary.id.eq(diaryId)
                        .and(diary.status.eq(DiaryStatus.PUBLISHED))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetchOne();
    }
}