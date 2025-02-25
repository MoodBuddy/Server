package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import java.time.LocalDate;

public interface DiaryService {
    Long saveDiary(final Long userId, DiaryReqSaveDTO diaryReqSaveDTO);
    Long updateDiary(final Long userId, DiaryReqUpdateDTO diaryReqUpdateDTO);
    LocalDate deleteDiary(final Long userId, final Long diaryId);
    DiaryResDetailDTO getDiary(final Long userId, final Long diaryId);
    Diary findDiaryById(Long diaryId);
    void validateExistingDiary(final Long userId, LocalDate diaryDate);
}
