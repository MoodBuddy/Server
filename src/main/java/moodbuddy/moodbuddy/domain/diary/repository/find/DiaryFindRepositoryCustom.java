package moodbuddy.moodbuddy.domain.diary.repository.find;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindRepositoryCustom {
    Page<DiaryResDetailDTO> findAllByUserIdWithPageable(Long userId, Pageable pageable);
    Page<DiaryResDetailDTO> findAllByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable);
    Page<DiaryResDetailDTO> findAllByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable);
}
