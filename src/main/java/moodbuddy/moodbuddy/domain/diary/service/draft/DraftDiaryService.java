package moodbuddy.moodbuddy.domain.diary.service.draft;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;

import java.util.List;

public interface DraftDiaryService {
    // 일기 임시 저장
    Diary save(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);

    // 일기 임시 저장 날짜 조회
    List<DraftDiaryResFindOneDTO> findAll(final Long userId);

    // 일기 임시 저장 선택 삭제
    void selectDelete(DraftDiaryReqSelectDeleteDTO requestDTO, final Long userId);

    DraftDiaryResDetailDTO findOneByDiaryId(final Long diaryId, final Long userId);
}
