package moodbuddy.moodbuddy.domain.diary.repository.count;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

import java.time.LocalDate;

public interface DiaryCountRepositoryCustom {
    long countByEmotionAndDateRange(final Long userId, DiaryEmotion emotion, LocalDate start, LocalDate end);
    long countBySubjectAndDateRange(final Long userId, DiarySubject subject, LocalDate start, LocalDate end);
}
