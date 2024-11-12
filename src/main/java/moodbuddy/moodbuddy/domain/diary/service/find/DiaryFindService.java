package moodbuddy.moodbuddy.domain.diary.service.find;

import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFindService {
    // 일기 하나 조회
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId, final Long userId);

    // 일기 전체 조회 (페이징)
    Page<DiaryResDetailDTO> findAll(Pageable pageable, final Long userId);

    // 일기 비슷한 감정 조회
    Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId);

    // 상세검색 조회
    Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId);
}
