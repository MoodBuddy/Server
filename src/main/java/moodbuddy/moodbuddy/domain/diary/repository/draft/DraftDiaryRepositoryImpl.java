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
        List<DraftDiaryResFindOneDTO> result = queryFactory
                .select(Projections.constructor(DraftDiaryResFindOneDTO.class,
                        diary.diaryId,
                        diary.diaryDate,
                        diary.diaryStatus
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.diaryStatus.eq(DiaryStatus.DRAFT).and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE))))
                .fetch()
                .stream()
                .map(d -> new DraftDiaryResFindOneDTO(d.diaryId(), d.diaryDate(), d.diaryStatus()))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public DraftDiaryResDetailDTO findOneByDiaryId(Long diaryId) {
        System.out.println("======");
        System.out.println(diaryId);
        DraftDiaryResDetailDTO result = queryFactory.select(Projections.constructor(DraftDiaryResDetailDTO.class,
                        diary.diaryId,
                        diary.userId,
                        diary.diaryTitle,
                        diary.diaryDate,
                        diary.diaryContent,
                        diary.diaryWeather,
                        diary.diaryStatus,
                        diary.diaryFont,
                        diary.diaryFontSize,
                        diary.moodBuddyStatus
                ))
                .from(diary)
                .where(diary.diaryId.eq(diaryId)
                        .and(diary.diaryStatus.eq(DiaryStatus.DRAFT))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetchOne();

        List<String> diaryImgList = queryFactory.select(diaryImage.diaryImgURL)
                .from(diaryImage)
                .where(diaryImage.diaryId.eq(diaryId).and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch();

        result.setDiaryImgList(diaryImgList);

        return result;
    }
}
