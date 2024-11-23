package moodbuddy.moodbuddy.domain.diary.service.draft;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;

public interface DraftDiaryService {
    // 일기 임시 저장
    Diary save(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);

    // 일기 임시 저장 날짜 조회
    DiaryResDraftFindAllDTO findAll(final Long userId);

    // 일기 임시 저장 선택 삭제
    void selectDelete(DiaryReqDraftSelectDeleteDTO requestDTO, final Long userId);
}
