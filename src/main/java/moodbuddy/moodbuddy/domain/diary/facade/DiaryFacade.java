package moodbuddy.moodbuddy.domain.diary.facade;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;

public interface DiaryFacade {
    DiaryResSaveDTO save(DiaryReqSaveDTO requestDTO);
    DiaryResSaveDTO update(DiaryReqUpdateDTO requestDTO);
    void delete(final Long diaryId);
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId);
}
