package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import java.time.LocalDate;
import java.util.Map;

public interface DiaryService {
    // 일기 저장
    Diary saveDiary(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);

    // 일기 수정
    Diary updateDiary(DiaryReqUpdateDTO diaryReqUpdateDTO, final Long userId);

    // 일기 삭제
    Diary deleteDiary(Long diaryId, Long userId);
    DiaryResDetailDTO getDiary(final Long diaryId, final Long userId);

    Diary findDiaryById(Long diaryId);
    void validateDiaryAccess(Diary findDiary, Long userId);
    void validateExistingDiary(LocalDate diaryDate, Long userId);
}
