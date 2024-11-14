package moodbuddy.moodbuddy.domain.diary.service.count;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

import java.time.LocalDate;

public interface DiaryCountService {
    // 감정 갯수 검색
    long getDiaryEmotionCount(DiaryEmotion diaryEmotion, LocalDate start, LocalDate end);

    // 주제 갯수 검색
    long getDiarySubjectCount(DiarySubject subject, LocalDate start, LocalDate end);
}
