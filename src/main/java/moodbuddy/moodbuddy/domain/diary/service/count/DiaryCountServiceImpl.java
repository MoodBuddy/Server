package moodbuddy.moodbuddy.domain.diary.service.count;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.infra.batch.QuddyTIBatchJDBCRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryCountServiceImpl implements DiaryCountService {
    private final QuddyTIBatchJDBCRepository quddyTIBatchJDBCRepository;

    @Override
    public Map<DiaryEmotion, Long> getEmotionCountsByDate(final Long userId, LocalDate[] dates) {
        EnumMap<DiaryEmotion, Long> emotionCounts = new EnumMap<>(DiaryEmotion.class);
        for (DiaryEmotion emotion : DiaryEmotion.values()) {
            long count = quddyTIBatchJDBCRepository.findEmotionCountsByUserIdAndDate(userId, emotion, dates[0], dates[1]);
            emotionCounts.put(emotion, count);
        }
        return emotionCounts;
    }

    @Override
    public Map<DiarySubject, Long> getSubjectCountsByDate(final Long userId, LocalDate[] dates) {
        EnumMap<DiarySubject, Long> subjectCounts = new EnumMap<>(DiarySubject.class);
        for (DiarySubject subject : DiarySubject.values()) {
            long count = quddyTIBatchJDBCRepository.findSubjectCountsByUserIdAndDate(userId, subject, dates[0], dates[1]);
            subjectCounts.put(subject, count);
        }
        return subjectCounts;
    }
}