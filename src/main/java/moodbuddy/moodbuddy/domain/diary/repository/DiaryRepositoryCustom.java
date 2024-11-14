package moodbuddy.moodbuddy.domain.diary.repository;

import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import java.time.LocalDate;

public interface DiaryRepositoryCustom {
    DiaryResDetailDTO findOneByDiaryId(Long diaryId);
    long countByEmotionAndDateRange(DiaryEmotion emotion, LocalDate start, LocalDate end);
    long countBySubjectAndDateRange(DiarySubject subject, LocalDate start, LocalDate end);
}
