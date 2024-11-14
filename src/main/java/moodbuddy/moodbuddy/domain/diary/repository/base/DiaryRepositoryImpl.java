package moodbuddy.moodbuddy.domain.diary.repository.base;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.base.*;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static moodbuddy.moodbuddy.domain.diary.domain.base.QDiary.diary;
import static moodbuddy.moodbuddy.domain.diary.domain.image.QDiaryImage.diaryImage;


@Slf4j
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
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
                        .and(diary.diaryStatus.eq(DiaryStatus.DRAFT)))
                .fetch()
                .stream()
                .map(d -> new DiaryResDraftFindOneDTO(d.diaryId(), d.userId(), d.diaryDate(), d.diaryStatus()))
                .collect(Collectors.toList());

        return new DiaryResDraftFindAllDTO(draftList);
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
                .where(diary.diaryId.eq(diaryId))
                .fetchOne();

        List<String> diaryImgList = queryFactory.select(diaryImage.diaryImgURL)
                .from(diaryImage)
                .where(diaryImage.diary.diaryId.eq(diaryId))
                .fetch();

        diaryResDetailDTO.setDiaryImgList(diaryImgList);

        return diaryResDetailDTO;
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByUserIdWithPageable(Long userId, Pageable pageable) {
        List<Diary> diaries = queryFactory.selectFrom(diary)
                .where(diary.userId.eq(userId)
                        .and(diary.diaryStatus.eq(DiaryStatus.PUBLISHED)))
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
                    .where(diaryImage.diary.diaryId.eq(d.getDiaryId()))
                    .fetch();

            return DiaryResDetailDTO.builder()
                    .diaryId(d.getDiaryId())
                    .diaryTitle(d.getDiaryTitle())
                    .diaryDate(d.getDiaryDate())
                    .diaryContent(d.getDiaryContent())
                    .diaryWeather(d.getDiaryWeather())
                    .diaryEmotion(d.getDiaryEmotion())
                    .diaryStatus(d.getDiaryStatus())
                    .diarySummary(d.getDiarySummary())
                    .diarySubject(d.getDiarySubject())
                    .userId(d.getUserId())
                    .diaryImgList(diaryImgList)
                    .diaryBookMarkCheck(d.getDiaryBookMarkCheck())
                    .diaryFont(d.getDiaryFont())
                    .diaryFontSize(d.getDiaryFontSize())
                    .moodBuddyStatus(d.getMoodBuddyStatus())
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
                .where(diaryEmotionEq(emotion).and(diary.userId.eq(userId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<String>> diaryImages = queryFactory.selectFrom(diaryImage)
                .where(diaryImage.diary.diaryId.in(diaries.stream().map(Diary::getDiaryId).collect(Collectors.toList())))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        diaryImage -> diaryImage.getDiary().getDiaryId(),
                        Collectors.mapping(DiaryImage::getDiaryImgURL, Collectors.toList())
                ));

        List<DiaryResDetailDTO> dtoList = diaries.stream()
                .map(d -> new DiaryResDetailDTO(
                        d.getDiaryId(),
                        d.getUserId(),
                        d.getDiaryTitle(),
                        d.getDiaryDate(),
                        d.getDiaryContent(),
                        d.getDiaryWeather(),
                        d.getDiaryEmotion(),
                        d.getDiaryStatus(),
                        d.getDiarySummary(),
                        d.getDiarySubject(),
                        d.getDiaryBookMarkCheck(),
                        diaryImages.getOrDefault(d.getDiaryId(), List.of()),
                        d.getDiaryFont(),
                        d.getDiaryFontSize(),
                        d.getMoodBuddyStatus()
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
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(diary)
                .where(builder)
                .fetchCount();

        List<Long> diaryIds = results.stream().map(Diary::getDiaryId).collect(Collectors.toList());

        Map<Long, List<String>> diaryImagesMap = queryFactory
                .selectFrom(diaryImage)
                .where(diaryImage.diary.diaryId.in(diaryIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        diaryImage -> diaryImage.getDiary().getDiaryId(),
                        Collectors.mapping(DiaryImage::getDiaryImgURL, Collectors.toList())
                ));

        List<DiaryResDetailDTO> dtoList = results.stream()
                .map(d -> DiaryResDetailDTO.builder()
                        .diaryId(d.getDiaryId())
                        .userId(d.getUserId())
                        .diaryTitle(d.getDiaryTitle())
                        .diaryDate(d.getDiaryDate())
                        .diaryContent(d.getDiaryContent())
                        .diaryWeather(d.getDiaryWeather())
                        .diaryEmotion(d.getDiaryEmotion())
                        .diaryStatus(d.getDiaryStatus())
                        .diarySummary(d.getDiarySummary())
                        .diarySubject(d.getDiarySubject())
                        .diaryImgList(diaryImagesMap.getOrDefault(d.getDiaryId(), List.of()))
                        .diaryFont(d.getDiaryFont())
                        .diaryFontSize(d.getDiaryFontSize())
                        .moodBuddyStatus(d.getMoodBuddyStatus())
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
        // 날짜의 월을 비교하는 조건 추가
        return diary.diaryDate.month().eq(month);
    }

    private BooleanExpression diaryEmotionEq(DiaryEmotion emotion) {
        return emotion != null ? diary.diaryEmotion.eq(emotion) : null;
    }

    private BooleanExpression diarySubjectEq(DiarySubject subject) {
        return subject != null ? diary.diarySubject.eq(subject) : null;
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