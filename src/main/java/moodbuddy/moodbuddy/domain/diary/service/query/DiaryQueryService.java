package moodbuddy.moodbuddy.domain.diary.service.query;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface DiaryQueryService {
    PageCustom<DiaryResQueryDTO> getDiaries(Pageable pageable, final Long userId);
    PageCustom<DiaryResQueryDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId);
    PageCustom<DiaryResQueryDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId);
}
