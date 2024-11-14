package moodbuddy.moodbuddy.domain.diary.facade;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;

public interface DiaryFacade {
    DiaryResDetailDTO saveDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDetailDTO updateDiary(DiaryReqUpdateDTO requestDTO);
    void deleteDiary(final Long diaryId);
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId);
}
