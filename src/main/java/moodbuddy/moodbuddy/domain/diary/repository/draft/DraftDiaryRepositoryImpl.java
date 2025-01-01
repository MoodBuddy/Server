package moodbuddy.moodbuddy.domain.diary.repository.draft;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import java.util.List;
import java.util.stream.Collectors;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;
import static moodbuddy.moodbuddy.domain.diary.domain.image.QDiaryImage.diaryImage;

public class DraftDiaryRepositoryImpl implements DraftDiaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DraftDiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DraftDiaryResFindOneDTO> findAllByUserId(Long userId) {
        return queryFactory
                .select(Projections.constructor(DraftDiaryResFindOneDTO.class,
                        diary.id,
                        diary.date,
                        diary.status
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.status.eq(DiaryStatus.DRAFT).and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE))))
                .fetch()
                .stream()
                .map(d -> new DraftDiaryResFindOneDTO(d.diaryId(), d.diaryDate(), d.diaryStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public DraftDiaryResDetailDTO findOneByDiaryId(Long diaryId) {
        var result = queryFactory.select(Projections.constructor(DraftDiaryResDetailDTO.class,
                        diary.id,
                        diary.userId,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.weather,
                        diary.status,
                        diary.font,
                        diary.fontSize,
                        diary.moodBuddyStatus
                ))
                .from(diary)
                .where(diary.id.eq(diaryId)
                        .and(diary.status.eq(DiaryStatus.DRAFT))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetchOne();

        var diaryImgList = queryFactory.select(diaryImage.imageUrl)
                .from(diaryImage)
                .where(diaryImage.diaryId.eq(diaryId).and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();

        result.setDiaryImgList(diaryImgList);

        return result;
    }
}
