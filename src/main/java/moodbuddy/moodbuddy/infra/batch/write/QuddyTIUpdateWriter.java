package moodbuddy.moodbuddy.infra.batch.write;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.domain.type.QuddyTIType;
import moodbuddy.moodbuddy.domain.quddyTI.exception.QuddyTIInvalidateException;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.global.util.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuddyTIUpdateWriter implements ItemWriter<QuddyTI> {
    private final JdbcTemplate jdbcTemplate;
    private final DiaryCountService diaryCountService;
    private static final String UPDATE_SQL = "UPDATE quddy_ti SET " +
            "diary_frequency = ?, daily_count = ?, growth_count = ?, emotion_count = ?, travel_count = ?, " +
            "happiness_count = ?, anger_count = ?, disgust_count = ?, fear_count = ?, neutral_count = ?, " +
            "sadness_count = ?, surprise_count = ?, quddy_ti = ?, mood_buddy_status = ?, updated_at = NOW() " +
            "WHERE user_id = ? AND year = ? AND month = ?";

    @Override
    public void write(@NotNull Chunk<? extends QuddyTI> chunk) {
        List<Object[]> batchArgs = new ArrayList<>();
        for (QuddyTI quddyTI : chunk.getItems()) {
            LocalDate[] dateRange = DateUtils.getLastMonthDates();
            final Long userId = quddyTI.getUserId();
            Map<DiaryEmotion, Long> emotionCounts = diaryCountService.getEmotionCountsByDate(userId, dateRange);
            Map<DiarySubject, Long> subjectCounts = diaryCountService.getSubjectCountsByDate(userId, dateRange);
            String quddyTIType = generateType(emotionCounts, subjectCounts);

            batchArgs.add(new Object[]{
                    quddyTI.getDiaryFrequency(), quddyTI.getDailyCount(), quddyTI.getGrowthCount(),
                    quddyTI.getEmotionCount(), quddyTI.getTravelCount(), quddyTI.getHappinessCount(),
                    quddyTI.getAngerCount(), quddyTI.getDisgustCount(), quddyTI.getFearCount(),
                    quddyTI.getNeutralCount(), quddyTI.getSadnessCount(), quddyTI.getSurpriseCount(),
                    quddyTIType, MoodBuddyStatus.ACTIVE.name(), userId, quddyTI.getYear(), quddyTI.getMonth()
            });
        }
        jdbcTemplate.batchUpdate(UPDATE_SQL, batchArgs);
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
}