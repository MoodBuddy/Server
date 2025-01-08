package moodbuddy.moodbuddy.domain.quddyTI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.domain.type.QuddyTIType;
import moodbuddy.moodbuddy.domain.quddyTI.repository.QuddyTIRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.quddyTI.QuddyTIInvalidateException;
import moodbuddy.moodbuddy.global.common.exception.quddyTI.QuddyTINotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class QuddyTIServiceImpl implements QuddyTIService {
    private final QuddyTIRepository quddyTIRepository;
    private static final String formatMonth = "%02d";

    @Override
    public QuddyTI getQuddyTIByDate(final Long userId, String year, String month) {
        return getQuddyTIByUserIdAndDate(userId, year, month);
    }

    @Override
    @Transactional
    public void createNewMonth(final Long userId, LocalDate currentDate) {
        quddyTIRepository.save(QuddyTI.of(userId, formatYear(currentDate), formatMonth(currentDate)));
    }

    @Override
    @Transactional
    public void processLastMonth(final Long userId, LocalDate[] dateRange, Map<DiaryEmotion, Long> emotionCounts, Map<DiarySubject, Long> subjectCounts) {
        String quddyTIType = generateType(emotionCounts, subjectCounts);
        QuddyTI findQuddyTI = findAndValidateQuddyTI(userId, dateRange);
        updateLastMonth(findQuddyTI, emotionCounts, subjectCounts, quddyTIType);
    }

    private QuddyTI findAndValidateQuddyTI(Long userId, LocalDate[] dateRange) {
        return getQuddyTIByUserIdAndDate(userId, formatYear(dateRange[0]), formatMonth(dateRange[1]));
    }

    private String generateType(Map<DiaryEmotion, Long> emotionCounts, Map<DiarySubject, Long> subjectCounts) {
        String diaryType = determineDiaryFrequent(emotionCounts);
        if(QuddyTIType.NO_DIARY.getValue().equals(diaryType)) {
            return QuddyTIType.NO_DIARY.getValue();
        }
        String mostFrequentSubject = determineMostFrequentSubject(subjectCounts);
        String mostFrequentEmotion = determineMostFrequentEmotion(emotionCounts);
        return diaryType + mostFrequentSubject + mostFrequentEmotion;
    }

    private String determineDiaryFrequent(Map<DiaryEmotion, Long> emotionCounts) {
        long totalDiaryCount = calculateTotalCount(emotionCounts);
        if (totalDiaryCount == 0) {
            return QuddyTIType.NO_DIARY.getValue();
        }
        return totalDiaryCount >= 15 ? QuddyTIType.TYPE_J.getValue() : QuddyTIType.TYPE_P.getValue();
    }

    private String determineMostFrequentSubject(Map<DiarySubject, Long> subjectCounts) {
        return findMostFrequentEntry(subjectCounts)
                .map(subject -> subject.name().substring(0, 1))
                .orElseThrow(() -> new QuddyTIInvalidateException(ErrorCode.QUDDYTI_INVALIDATE));
    }

    private String determineMostFrequentEmotion(Map<DiaryEmotion, Long> emotionCounts) {
        return (findMostFrequentEntry(emotionCounts)
                .map(this::getEmotionAbbreviation)
                .orElseThrow(() -> new QuddyTIInvalidateException(ErrorCode.QUDDYTI_INVALIDATE)));
    }

    private long calculateTotalCount(Map<?, Long> counts) {
        return counts.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private <T> Optional<T> findMostFrequentEntry(Map<T, Long> counts) {
        return counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    private String getEmotionAbbreviation(DiaryEmotion emotion) {
        return Optional.ofNullable(emotion)
                .map(DiaryEmotion::getAbbreviation)
                .orElse(QuddyTIType.NO_DIARY.getValue());
    }

    private void updateLastMonth(QuddyTI quddyTI,
                                 Map<DiaryEmotion, Long> emotionCounts,
                                 Map<DiarySubject, Long> subjectCounts,
                                 String quddyTIType) {
        quddyTI.updateQuddyTI(emotionCounts, subjectCounts, quddyTIType);
    }

    private QuddyTI getQuddyTIByUserIdAndDate(Long userId, String year, String month) {
        return quddyTIRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.QUDDYTI_NOT_FOUND));
    }

    private String formatYear(LocalDate date) {
        return String.valueOf(date.getYear());
    }

    private String formatMonth(LocalDate date) {
        return String.format(formatMonth, date.getMonthValue());
    }
}