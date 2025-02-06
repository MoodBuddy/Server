package moodbuddy.moodbuddy.domain.diary.service.count;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

import java.time.LocalDate;
import java.util.Map;

public interface DiaryCountService {
    Map<DiaryEmotion, Long> getEmotionCountsByDate(final Long userId, LocalDate[] dates);
    Map<DiarySubject, Long> getSubjectCountsByDate(final Long userId, LocalDate[] dates);
}
