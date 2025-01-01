package moodbuddy.moodbuddy.domain.diary.repository.find;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindRepositoryCustom {
    Page<DiaryResFindDTO> findDiariesWithPageable(Long userId, Pageable pageable);
    Page<DiaryResFindDTO> findDiariesByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable);
    Page<DiaryResFindDTO> findDiariesByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable);
}
