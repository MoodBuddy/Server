package moodbuddy.moodbuddy.domain.diary.repository.find;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;
import static moodbuddy.moodbuddy.domain.diary.domain.image.QDiaryImage.diaryImage;

public class DiaryFindRepositoryImpl implements DiaryFindRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DiaryFindRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByUserIdWithPageable(Long userId, Pageable pageable) {
        List<Diary> diaries = queryFactory.selectFrom(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.diaryStatus.eq(DiaryStatus.PUBLISHED))
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)
                        ))
                .orderBy(pageable.getSort().stream()
                        .map(order -> new OrderSpecifier(
                                order.isAscending() ? Order.ASC : Order.DESC,
                                new PathBuilder<>(diary.getType(), diary.getMetadata())
                                        .get(order.getProperty())))
                        .toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<DiaryResDetailDTO> diaryList = diaries.stream().map(d -> {
            List<String> diaryImgList = queryFactory.select(diaryImage.diaryImgURL)
                    .from(diaryImage)
                    .where(diaryImage.diaryId.eq(d.getDiaryId()).and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                    .fetch();

            return DiaryResDetailDTO.builder()
                    .diaryId(d.getDiaryId())
                    .diaryTitle(d.getDiaryTitle())
                    .diaryDate(d.getDiaryDate())
                    .diaryContent(d.getDiaryContent())
                    .diaryWeather(d.getDiaryWeather())
                    .diaryEmotion(d.getDiaryEmotion())
                    .diaryImgList(diaryImgList)
                    .diaryBookMarkCheck(d.getDiaryBookMarkCheck())
                    .diaryFont(d.getDiaryFont())
                    .diaryFontSize(d.getDiaryFontSize())
                    .build();
        }).collect(Collectors.toList());

        long total = queryFactory.selectFrom(diary)
                .where(diary.userId.eq(userId))
                .fetchCount();

        return new PageImpl<>(diaryList, pageable, total);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable) {
        List<Diary> diaries = queryFactory.selectFrom(diary)
                .where(diaryEmotionEq(emotion).and(diary.userId.eq(userId)
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)
                        .and(diary.diaryStatus.eq(DiaryStatus.PUBLISHED))
                        )))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<String>> diaryImages = queryFactory.selectFrom(diaryImage)
                .where(diaryImage.diaryId.in(diaries.stream().map(Diary::getDiaryId).collect(Collectors.toList()))
                        .and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        DiaryImage::getDiaryId,
                        Collectors.mapping(DiaryImage::getDiaryImgURL, Collectors.toList())
                ));

        List<DiaryResDetailDTO> dtoList = diaries.stream()
                .map(d -> new DiaryResDetailDTO(
                        d.getDiaryId(),
                        d.getDiaryTitle(),
                        d.getDiaryDate(),
                        d.getDiaryContent(),
                        d.getDiaryWeather(),
                        d.getDiaryEmotion(),
                        d.getDiaryBookMarkCheck(),
                        diaryImages.getOrDefault(d.getDiaryId(), List.of()),
                        d.getDiaryFont(),
                        d.getDiaryFontSize()
                ))
                .collect(Collectors.toList());

        long total = queryFactory.selectFrom(diary)
                .where(diaryEmotionEq(emotion).and(diary.userId.eq(userId)))
                .fetchCount();

        return new PageImpl<>(dtoList, pageable, total);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(diary.userId.eq(userId));

        if (filterDTO.year() != null) {
            startDate = LocalDate.of(filterDTO.year(), 1, 1);
            endDate = startDate.plusYears(1);
            builder.and(betweenDates(startDate, endDate));
        }

        if (filterDTO.month() != null) {
            builder.and(monthMatches(filterDTO.month()));
        }

        if (filterDTO.keyWord() != null && !filterDTO.keyWord().isEmpty()) {
            builder.and(containsKeyword(filterDTO.keyWord()));
        }

        if (filterDTO.diaryEmotion() != null) {
            builder.and(diaryEmotionEq(filterDTO.diaryEmotion()));
        }

        if (filterDTO.diarySubject() != null) {
            builder.and(diarySubjectEq(filterDTO.diarySubject()));
        }

        List<Diary> results = queryFactory.selectFrom(diary)
                .where(builder
                        .and(diary.diaryStatus.eq(DiaryStatus.PUBLISHED)
                        .and(diary.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(diary)
                .where(builder)
                .fetchCount();

        List<Long> diaryIds = results.stream().map(Diary::getDiaryId).collect(Collectors.toList());

        Map<Long, List<String>> diaryImagesMap = queryFactory
                .selectFrom(diaryImage)
                .where(diaryImage.diaryId.in(diaryIds)
                        .and(diaryImage.moodBuddyStatus.eq(MoodBuddyStatus.ACTIVE)))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        DiaryImage::getDiaryId,
                        Collectors.mapping(DiaryImage::getDiaryImgURL, Collectors.toList())
                ));

        List<DiaryResDetailDTO> dtoList = results.stream()
                .map(d -> DiaryResDetailDTO.builder()
                        .diaryId(d.getDiaryId())
                        .diaryTitle(d.getDiaryTitle())
                        .diaryDate(d.getDiaryDate())
                        .diaryContent(d.getDiaryContent())
                        .diaryWeather(d.getDiaryWeather())
                        .diaryEmotion(d.getDiaryEmotion())
                        .diaryImgList(diaryImagesMap.getOrDefault(d.getDiaryId(), List.of()))
                        .diaryFont(d.getDiaryFont())
                        .diaryFontSize(d.getDiaryFontSize())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return diary.diaryTitle.containsIgnoreCase(keyword)
                .or(diary.diaryContent.containsIgnoreCase(keyword))
                .or(diary.diarySummary.containsIgnoreCase(keyword));
    }

    private BooleanExpression betweenDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return diary.diaryDate.between(startDate, endDate);
        } else if (startDate != null) {
            return diary.diaryDate.goe(startDate);
        } else if (endDate != null) {
            return diary.diaryDate.loe(endDate);
        } else {
            return null;
        }
    }

    private BooleanExpression monthMatches(Integer month) {
        if (month == null) {
            return null;
        }
        return diary.diaryDate.month().eq(month);
    }

    private BooleanExpression diaryEmotionEq(DiaryEmotion emotion) {
        return emotion != null ? diary.diaryEmotion.eq(emotion) : null;
    }

    private BooleanExpression diarySubjectEq(DiarySubject subject) {
        return subject != null ? diary.diarySubject.eq(subject) : null;
    }
}
