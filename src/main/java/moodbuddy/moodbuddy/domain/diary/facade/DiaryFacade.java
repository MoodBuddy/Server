package moodbuddy.moodbuddy.domain.diary.facade;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;

public interface DiaryFacade {
    DiaryResDetailDTO save(DiaryReqSaveDTO requestDTO);
    DiaryResDetailDTO update(DiaryReqUpdateDTO requestDTO);
    void delete(final Long diaryId);
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId);
}
