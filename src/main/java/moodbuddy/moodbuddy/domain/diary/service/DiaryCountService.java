package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.DiarySubject;

import java.time.LocalDate;

public interface DiaryCountService {
    // 감정 갯수 검색
    long getDiaryEmotionCount(DiaryEmotion diaryEmotion, LocalDate start, LocalDate end);

    // 주제 갯수 검색
    long getDiarySubjectCount(DiarySubject subject, LocalDate start, LocalDate end);
}
