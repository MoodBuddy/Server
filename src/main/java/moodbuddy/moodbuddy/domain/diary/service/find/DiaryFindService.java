package moodbuddy.moodbuddy.domain.diary.service.find;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindService {
    Page<DiaryResFindDTO> getDiaries(Pageable pageable, final Long userId);
    Page<DiaryResFindDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId);
    Page<DiaryResFindDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId);
}
