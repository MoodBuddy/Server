package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;

import java.time.LocalDate;
import java.util.Map;

public interface DiaryService {
    // 일기 저장
    Diary save(DiaryReqSaveDTO diaryReqSaveDTO, Map<String, String> gptResults, final Long userId);

    // 일기 수정
    Diary update(DiaryReqUpdateDTO diaryReqUpdateDTO, Map<String, String> gptResults, final Long userId);

    // 일기 삭제
    Diary delete(Long diaryId, Long userId);

    Diary getDiaryById(Long diaryId);
    void validateDiaryAccess(Diary findDiary, Long userId);
    void validateExistingDiary(LocalDate diaryDate, Long userId);
}
