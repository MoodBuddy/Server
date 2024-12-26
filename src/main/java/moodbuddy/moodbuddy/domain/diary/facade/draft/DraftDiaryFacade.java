package moodbuddy.moodbuddy.domain.diary.facade.draft;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;

import java.util.List;

public interface DraftDiaryFacade {
    DiaryResSaveDTO saveDraftDiary(DiaryReqSaveDTO requestDTO);
    DiaryResSaveDTO updateDraftDiary(DiaryReqUpdateDTO requestDTO);
    List<DraftDiaryResFindOneDTO> getDraftDiaries();
    void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO);
    DraftDiaryResDetailDTO getDraftDiary(Long diaryId);
}
