package moodbuddy.moodbuddy.domain.diary.facade.find;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindFacade {
    Page<DiaryResDetailDTO> getDiaries(Pageable pageable);
    Page<DiaryResDetailDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable);
    Page<DiaryResDetailDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable);
}
