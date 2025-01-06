package moodbuddy.moodbuddy.domain.diary.facade.query;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface DiaryQueryFacade {
    PageCustom<DiaryResQueryDTO> getDiaries(Pageable pageable);
    PageCustom<DiaryResQueryDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable);
    PageCustom<DiaryResQueryDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable);
}
