package moodbuddy.moodbuddy.domain.diary.repository.base;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiarySubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface DiaryRepositoryCustom {
    DiaryResDraftFindAllDTO draftFindAllByUserId(Long userId);
    DiaryResDetailDTO findOneByDiaryId(Long diaryId);
    Page<DiaryResDetailDTO> findAllByUserIdWithPageable(Long userId, Pageable pageable);
    Page<DiaryResDetailDTO> findAllByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable);
    Page<DiaryResDetailDTO> findAllByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable);
    long countByEmotionAndDateRange(DiaryEmotion emotion, LocalDate start, LocalDate end);
    long countBySubjectAndDateRange(DiarySubject subject, LocalDate start, LocalDate end);
}
