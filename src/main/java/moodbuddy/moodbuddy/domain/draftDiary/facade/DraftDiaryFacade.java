package moodbuddy.moodbuddy.domain.draftDiary.facade;

import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;

import java.util.List;

public interface DraftDiaryFacade {
    DiaryResSaveDTO saveDraftDiary(DiaryReqSaveDTO requestDTO);
    DiaryResSaveDTO updateDraftDiary(DiaryReqUpdateDTO requestDTO);
    List<DraftDiaryResFindOneDTO> getDraftDiaries();
    void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO);
    DraftDiaryResDetailDTO getDraftDiary(Long diaryId);
}
