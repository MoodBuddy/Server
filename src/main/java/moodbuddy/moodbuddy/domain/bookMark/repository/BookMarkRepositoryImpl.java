package moodbuddy.moodbuddy.domain.bookMark.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;

public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DiaryResFindDTO> getBookMarksWithPageable(Long userId, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(DiaryResFindDTO.class,
                        diary.id,
                        diary.title,
                        diary.date,
                        diary.content,
                        diary.thumbnail
                ))
                .from(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.bookMark.eq(true))
                        .and(diary.status.eq(DiaryStatus.PUBLISHED))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = getTotal(userId);

        return new PageImpl<>(results, pageable, total);
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
