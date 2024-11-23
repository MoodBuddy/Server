package moodbuddy.moodbuddy.domain.diary.facade.draft;

import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;

import java.util.List;

public interface DraftDiaryFacade {
    DiaryResDetailDTO save(DiaryReqSaveDTO requestDTO);
    List<DraftDiaryResFindOneDTO> findAll();
    void selectDelete(DraftDiaryReqSelectDeleteDTO requestDTO);
    DiaryResDetailDTO findOneByDiaryId(Long diaryId);
}
