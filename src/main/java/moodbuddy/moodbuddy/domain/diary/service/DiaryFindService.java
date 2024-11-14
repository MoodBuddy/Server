package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;

public interface DiaryFindService {
    Diary findDiaryById(Long diaryId);
    void validateDiaryAccess(Diary findDiary, Long userId);
}
