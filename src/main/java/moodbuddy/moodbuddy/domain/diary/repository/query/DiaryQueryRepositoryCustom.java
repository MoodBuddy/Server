package moodbuddy.moodbuddy.domain.diary.repository.query;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.query.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface DiaryQueryRepositoryCustom {
    PageCustom<DiaryResQueryDTO> findDiariesWithPageable(Long userId, Pageable pageable);
    PageCustom<DiaryResQueryDTO> findDiariesByEmotionWithPageable(Long userId, DiaryEmotion emotion, Pageable pageable);
    PageCustom<DiaryResQueryDTO> findDiariesByFilterWithPageable(Long userId, DiaryReqFilterDTO filterDTO, Pageable pageable);
}
