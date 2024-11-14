package moodbuddy.moodbuddy.domain.quddyTI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.mapper.QuddyTIMapper;
import moodbuddy.moodbuddy.domain.quddyTI.repository.QuddyTIRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.quddyTI.QuddyTINotFoundException;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class QuddyTIServiceImpl implements QuddyTIService {

    private final QuddyTIRepository quddyTIRepository;
    private final DiaryCountService diaryCountService;

    /** QuddyTI 월 별 조회 **/
    @Override
    public List<QuddyTIResDetailDTO> findAll() {
        return getQuddyTIList(JwtUtil.getUserId()).stream()
                .map(QuddyTIMapper::toQuddyTIResDetailDTO)
                .collect(Collectors.toList());
    }

    private List<QuddyTI> getQuddyTIList(Long userId) {
        return quddyTIRepository.findByUserId(userId)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.NOT_FOUND_QUDDYTI));
    }

    /** QuddyTI 저장(스케줄링) **/
    @Override
    @Transactional
    public void aggregateAndSaveDiaryData() {
        final Long userId = JwtUtil.getUserId();
        LocalDate[] lastMonthRange = getLastMonthDateRange();
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);

        // 현재 월의 QuddyTI 생성
        createNewMonthQuddyTI(userId, firstDayOfCurrentMonth);

        // 이전 월의 QuddyTI 업데이트
        updateLastMonthQuddyTI(userId, lastMonthRange[0], lastMonthRange[1]);
    }

    private void createNewMonthQuddyTI(Long userId, LocalDate currentMonth) {
        String yearMonth = formatYearMonth(currentMonth);
        quddyTIRepository.save(QuddyTIMapper.toQuddyTIEntity(userId, yearMonth));
    }

    private void updateLastMonthQuddyTI(Long userId, LocalDate start, LocalDate end) {
        String yearMonth = formatYearMonth(start);

        QuddyTI lastMonthQuddyTI = getLastMonthQuddyTI(userId, yearMonth);

        Map<DiaryEmotion, Long> emotionCounts = getDiaryEmotionCounts(start, end);
        Map<DiarySubject, Long> subjectCounts = getDiarySubjectCounts(start, end);
        String quddyTIType = determineQuddyTIType(emotionCounts, subjectCounts);

        lastMonthQuddyTI.updateQuddyTI(emotionCounts, subjectCounts, quddyTIType);
    }

    private LocalDate[] getLastMonthDateRange() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        return new LocalDate[]{lastMonth.atDay(1), lastMonth.atEndOfMonth()};
    }

    private String formatYearMonth(LocalDate date) {
        return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
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

    private QuddyTI getLastMonthQuddyTI(Long userId, String yearMonth) {
        return quddyTIRepository.findByUserIdAndQuddyTIYearMonth(userId, yearMonth)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.NOT_FOUND_QUDDYTI));
    }
}