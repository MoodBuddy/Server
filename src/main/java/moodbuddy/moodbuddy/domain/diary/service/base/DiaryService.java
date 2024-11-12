package moodbuddy.moodbuddy.domain.diary.service.base;

import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DiaryService {
    // 일기 저장
    Diary save(DiaryReqSaveDTO diaryReqSaveDTO, Map<String, String> gptResults, final Long userId);

    // 일기 수정
    Diary update(DiaryReqUpdateDTO diaryReqUpdateDTO, Map<String, String> gptResults, final Long userId);

    // 일기 삭제
    Diary delete(Long diaryId, Long userId);

    // 일기 임시 저장
    Diary draftSave(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);

    // 일기 임시 저장 날짜 조회
    DiaryResDraftFindAllDTO draftFindAll(final Long userId);

    // 일기 임시 저장 선택 삭제
    List<Diary> draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO, final Long userId);

    Diary getDiaryById(Long diaryId);
    void validateDiaryAccess(Diary findDiary, Long userId);
    void validateExistingDiary(LocalDate diaryDate, Long userId);
}
