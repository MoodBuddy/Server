package moodbuddy.moodbuddy.domain.draftDiary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import java.util.List;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;
import static moodbuddy.moodbuddy.domain.diary.domain.image.QDiaryImage.diaryImage;

public class DraftDiaryRepositoryImpl implements DraftDiaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DraftDiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DraftDiaryResFindOneDTO> getDraftDiaries(Long userId) {
        return queryFactory.select(Projections.constructor(DraftDiaryResFindOneDTO.class,
                        diary.id,
                        diary.date
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();
    }

    @Override
    public DraftDiaryResDetailDTO getDraftDiaryById(Long diaryId) {
        var result = queryFactory.select(Projections.constructor(DraftDiaryResDetailDTO.class,
                        diary.id,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.weather,
                        diary.emotion,
                        diary.font,
                        diary.fontSize
                ))
                .from(diary)
                .where(diary.id.eq(diaryId)
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetchOne();

        var diaryImgList = queryFactory.select(diaryImage.imageUrl)
                .from(diaryImage)
                .where(diaryImage.diaryId.eq(diaryId).and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();

        result.saveDiaryImageUrls(diaryImgList);

        return result;
    }
}
