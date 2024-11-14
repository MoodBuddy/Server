package moodbuddy.moodbuddy.domain.diary.repository.count;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

import java.time.LocalDate;

public interface DiaryCountRepositoryCustom {
    long countByEmotionAndDateRange(DiaryEmotion emotion, LocalDate start, LocalDate end);
    long countBySubjectAndDateRange(DiarySubject subject, LocalDate start, LocalDate end);
}
