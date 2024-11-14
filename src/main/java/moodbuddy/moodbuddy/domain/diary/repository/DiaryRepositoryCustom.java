package moodbuddy.moodbuddy.domain.diary.repository;

import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;

public interface DiaryRepositoryCustom {
    DiaryResDetailDTO findOneByDiaryId(Long diaryId);
}
