package moodbuddy.moodbuddy.domain.diary.service.find;

import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindService {
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId, final Long userId);
    Page<DiaryResDetailDTO> findAll(Pageable pageable, final Long userId);
    Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId);
    Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId);
}
