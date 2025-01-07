package moodbuddy.moodbuddy.domain.draftDiary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import java.util.List;
import static moodbuddy.moodbuddy.domain.draftDiary.domain.QDraftDiary.draftDiary;
import static moodbuddy.moodbuddy.domain.draftDiary.domain.image.QDraftDiaryImage.draftDiaryImage;

public class DraftDiaryRepositoryImpl implements DraftDiaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DraftDiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DraftDiaryResFindOneDTO> getDraftDiaries(Long userId) {
        return queryFactory.select(Projections.constructor(DraftDiaryResFindOneDTO.class,
                        draftDiary.id,
                        draftDiary.date
                ))
                .from(draftDiary)
                .where(draftDiary.userId.eq(userId)
                        .and(draftDiary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();
    }

    @Override
    public DraftDiaryResDetailDTO getDraftDiaryById(Long draftDiaryId) {
        var result = queryFactory.select(Projections.constructor(DraftDiaryResDetailDTO.class,
                        draftDiary.id,
                        draftDiary.title,
                        draftDiary.date,
                        draftDiary.content,
                        draftDiary.weather,
                        draftDiary.font,
                        draftDiary.fontSize
                ))
                .from(draftDiary)
                .where(draftDiary.id.eq(draftDiaryId)
                        .and(draftDiary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetchOne();

        var diaryImgList = queryFactory.select(draftDiaryImage.imageUrl)
                .from(draftDiaryImage)
                .where(draftDiaryImage.draftDiaryId.eq(draftDiaryId).and(draftDiaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();

        result.saveDraftDiaryImageUrls(diaryImgList);

        return result;
    }
}
