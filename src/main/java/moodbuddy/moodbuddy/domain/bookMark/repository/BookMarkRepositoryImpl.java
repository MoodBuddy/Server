package moodbuddy.moodbuddy.domain.bookMark.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;
import static moodbuddy.moodbuddy.domain.bookMark.domain.QBookMark.bookMark;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;
import static moodbuddy.moodbuddy.domain.diary.domain.image.QDiaryImage.diaryImage;

public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllWithPageable(Long userId, Pageable pageable) {
        var diaries = queryFactory.selectFrom(diary)
                .join(bookMark).on(diary.id.eq(bookMark.diaryId))
                .where(bookMark.userId.eq(userId)
                        .and(diary.status.eq(DiaryStatus.PUBLISHED))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .orderBy(pageable.getSort().stream()
                        .map(order -> new OrderSpecifier(
                                order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                                new PathBuilder<>(diary.getType(), diary.getMetadata())
                                        .get(order.getProperty())))
                        .toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var diaryList = diaries.stream().map(d -> {
            List<String> diaryImgList = queryFactory.select(diaryImage.imageUrl)
                    .from(diaryImage)
                    .where(diaryImage.diaryId.eq(d.getId()))
                    .fetch();

            return DiaryResDetailDTO.builder()
                    .diaryId(d.getId())
                    .diaryTitle(d.getTitle())
                    .diaryDate(d.getDate())
                    .diaryContent(d.getContent())
                    .diaryWeather(d.getWeather())
                    .diaryEmotion(d.getEmotion())
                    .diaryBookMarkCheck(d.getBookMark())
                    .diaryFont(d.getFont())
                    .diaryFontSize(d.getFontSize())
                    .diaryImgList(diaryImgList)
                    .build();
        }).collect(Collectors.toList());

        var total = queryFactory.selectFrom(diary)
                .join(bookMark).on(diary.id.eq(bookMark.diaryId))
                .where(bookMark.userId.eq(userId))
                .fetchCount();

        return new PageImpl<>(diaryList, pageable, total);
    }
}
