package moodbuddy.moodbuddy.domain.diary.facade;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;

import java.io.IOException;

public interface DiaryFacade {
    DiaryResDetailDTO saveDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDetailDTO updateDiary(DiaryReqUpdateDTO requestDTO);
    void deleteDiary(final Long diaryId);
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId);
    DiaryImageResURLDTO uploadAndSaveDiaryImage(CloudReqDTO cloudReqDTO) throws IOException;
}
