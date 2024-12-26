package moodbuddy.moodbuddy.domain.diary.service.count;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.repository.count.DiaryCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryCountServiceImpl implements DiaryCountService {
    private final DiaryCountRepository diaryCountRepository;

    @Override
    public Map<DiaryEmotion, Long> getEmotionCounts(LocalDate[] dates) {
        EnumMap<DiaryEmotion, Long> emotionCounts = new EnumMap<>(DiaryEmotion.class);
        for (DiaryEmotion emotion : DiaryEmotion.values()) {
            long count = diaryCountRepository.countByEmotionAndDateRange(emotion, dates[0], dates[1]);
            emotionCounts.put(emotion, count);
        }
        return emotionCounts;
    }

    @Override
    public Map<DiarySubject, Long> getSubjectCounts(LocalDate[] dates) {
        EnumMap<DiarySubject, Long> subjectCounts = new EnumMap<>(DiarySubject.class);
        for (DiarySubject subject : DiarySubject.values()) {
            long count = diaryCountRepository.countBySubjectAndDateRange(subject, dates[0], dates[1]);
            subjectCounts.put(subject, count);
        }
        return subjectCounts;
    }
}
