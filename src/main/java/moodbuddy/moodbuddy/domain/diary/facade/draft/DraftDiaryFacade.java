package moodbuddy.moodbuddy.domain.diary.facade.draft;

import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;

public interface DraftDiaryFacade {
    DiaryResDetailDTO save(DiaryReqSaveDTO requestDTO);
    DiaryResDraftFindAllDTO findAll();
    void selectDelete(DiaryReqDraftSelectDeleteDTO requestDTO);
}
