package moodbuddy.moodbuddy.domain.diary.repository.draft;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindOneDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import java.util.List;
import java.util.stream.Collectors;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;

public class DraftDiaryRepositoryImpl implements DraftDiaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DraftDiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public DiaryResDraftFindAllDTO draftFindAllByUserId(Long userId) {
        List<DiaryResDraftFindOneDTO> draftList = queryFactory
                .select(Projections.constructor(DiaryResDraftFindOneDTO.class,
                        diary.diaryId,
                        diary.userId,
                        diary.diaryDate,
                        diary.diaryStatus
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.diaryStatus.eq(DiaryStatus.DRAFT).and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE))))
                .fetch()
                .stream()
                .map(d -> new DiaryResDraftFindOneDTO(d.diaryId(), d.userId(), d.diaryDate(), d.diaryStatus()))
                .collect(Collectors.toList());

        return new DiaryResDraftFindAllDTO(draftList);
    }
}
