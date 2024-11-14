package moodbuddy.moodbuddy.domain.diary.facade.base;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;

public interface DiaryFacade {
    DiaryResDetailDTO saveDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDetailDTO updateDiary(DiaryReqUpdateDTO requestDTO);
    void deleteDiary(final Long diaryId);
    DiaryResDetailDTO saveDraftDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDraftFindAllDTO draftFindAll();
    void draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO);
}
