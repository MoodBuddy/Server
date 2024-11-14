package moodbuddy.moodbuddy.domain.diary.facade.draft;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;

public interface DraftDiaryFacade {
    DiaryResDetailDTO saveDraftDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDraftFindAllDTO draftFindAll();
    void draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO);
}
