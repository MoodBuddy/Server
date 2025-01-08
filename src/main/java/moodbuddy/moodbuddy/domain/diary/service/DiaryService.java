package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import java.time.LocalDate;

public interface DiaryService {
    Diary saveDiary(final Long userId, DiaryReqSaveDTO diaryReqSaveDTO);
    Diary updateDiary(final Long userId, DiaryReqUpdateDTO diaryReqUpdateDTO);
    Diary deleteDiary(final Long userId, final Long diaryId);
    DiaryResDetailDTO getDiary(final Long userId, final Long diaryId);
    Diary findDiaryById(Long diaryId);
    void validateDiaryAccess(final Long userId, Diary findDiary);
    void validateExistingDiary(final Long userId, LocalDate diaryDate);
}
