package moodbuddy.moodbuddy.domain.draftDiary.facade;

import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqPublishDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResSaveDTO;

import java.util.List;

public interface DraftDiaryFacade {
    DraftDiaryResSaveDTO saveDraftDiary(DraftDiaryReqSaveDTO requestDTO);
    DiaryResSaveDTO publishDraftDiary(DraftDiaryReqPublishDTO requestDTO);
    List<DraftDiaryResFindOneDTO> getDraftDiaries();
    void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO);
    DraftDiaryResDetailDTO getDraftDiary(final Long diaryId);
}
