package moodbuddy.moodbuddy.domain.diary.facade.find;

import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindFacade {
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId);
    Page<DiaryResDetailDTO> findAll(Pageable pageable);
    Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable);
    Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable);
}
