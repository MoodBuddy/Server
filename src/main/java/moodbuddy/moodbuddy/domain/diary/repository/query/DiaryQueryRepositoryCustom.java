package moodbuddy.moodbuddy.domain.diary.repository.query;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface DiaryQueryRepositoryCustom {
    PageCustom<DiaryResQueryDTO> findDiariesWithPageable(Long userId, Pageable pageable);
    PageCustom<DiaryResQueryDTO> findDiariesByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable);
    PageCustom<DiaryResQueryDTO> findDiariesByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable);
}
