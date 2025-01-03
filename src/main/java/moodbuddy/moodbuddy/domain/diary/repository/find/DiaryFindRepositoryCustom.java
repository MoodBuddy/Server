package moodbuddy.moodbuddy.domain.diary.repository.find;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface DiaryFindRepositoryCustom {
    PageCustom<DiaryResFindDTO> findDiariesWithPageable(Long userId, Pageable pageable);
    PageCustom<DiaryResFindDTO> findDiariesByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable);
    PageCustom<DiaryResFindDTO> findDiariesByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable);
}
