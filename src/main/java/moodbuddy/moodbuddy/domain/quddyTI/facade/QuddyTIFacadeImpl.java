package moodbuddy.moodbuddy.domain.quddyTI.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.mapper.QuddyTIMapper;
import moodbuddy.moodbuddy.domain.quddyTI.service.QuddyTIService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuddyTIFacadeImpl implements QuddyTIFacade {
    private final DiaryCountService diaryCountService;
    private final QuddyTIService quddyTIService;
    private final QuddyTIMapper quddyTIMapper;

    @Override
    public QuddyTIResDetailDTO findByDate(String year, String month) {
        return quddyTIMapper.toResDetailDTO(quddyTIService.findByDate(JwtUtil.getUserId(), year, month));
    }

    @Override
    @Transactional
    public void aggregateAndSaveDiaryData() {
        Long userId = JwtUtil.getUserId();
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);

        quddyTIService.createNewMonthQuddyTI(userId, firstDayOfCurrentMonth);

        LocalDate[] lastMonthRange = getLastMonthDateRange();

        Map<DiaryEmotion, Long> emotionCounts = getDiaryEmotionCounts(lastMonthRange[0], lastMonthRange[1]);
        Map<DiarySubject, Long> subjectCounts = getDiarySubjectCounts(lastMonthRange[0], lastMonthRange[1]);
        String quddyTIType = determineQuddyTIType(emotionCounts, subjectCounts);
        quddyTIService.updateLastMonthQuddyTI(userId, lastMonthRange[0], emotionCounts, subjectCounts, quddyTIType);
    }

    private Map<DiaryEmotion, Long> getDiaryEmotionCounts(LocalDate start, LocalDate end) {
        return Arrays.stream(DiaryEmotion.values())
                .collect(Collectors.toMap(
                        emotion -> emotion,
                        emotion -> diaryCountService.getDiaryEmotionCount(emotion, start, end),
                        (a, b) -> b,
                        () -> new EnumMap<>(DiaryEmotion.class)
                ));
    }

    private Map<DiarySubject, Long> getDiarySubjectCounts(LocalDate start, LocalDate end) {
        return Arrays.stream(DiarySubject.values())
                .collect(Collectors.toMap(
                        subject -> subject,
                        subject -> diaryCountService.getDiarySubjectCount(subject, start, end),
                        (a, b) -> b,
                        () -> new EnumMap<>(DiarySubject.class)
                ));
    }

    private String determineQuddyTIType(Map<DiaryEmotion, Long> emotionCounts, Map<DiarySubject, Long> subjectCounts) {
        long totalDiaryCount = emotionCounts.values().stream().mapToLong(Long::longValue).sum();
        String diaryType = totalDiaryCount >= 15 ? "J" : "P";

        String mostFrequentSubject = subjectCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().name().substring(0, 1))
                .orElse("D");

        String mostFrequentEmotion = emotionCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> getEmotionAbbreviation(entry.getKey()))
                .orElse("H");

        return diaryType + mostFrequentSubject + mostFrequentEmotion;
    }

    private String getEmotionAbbreviation(DiaryEmotion emotion) {
        return switch (emotion) {
            case HAPPINESS -> "H";
            case ANGER -> "A";
            case DISGUST -> "D";
            case FEAR -> "F";
            case NEUTRAL -> "N";
            case SADNESS -> "Sa";
            case SURPRISE -> "Su";
        };
    }

    private LocalDate[] getLastMonthDateRange() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        return new LocalDate[]{lastMonth.atDay(1), lastMonth.atEndOfMonth()};
    }
}