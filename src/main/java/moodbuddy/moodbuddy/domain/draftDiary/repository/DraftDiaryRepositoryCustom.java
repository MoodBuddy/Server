package moodbuddy.moodbuddy.domain.draftDiary.repository;

import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;

import java.util.List;

public interface DraftDiaryRepositoryCustom {
    List<DraftDiaryResFindOneDTO> getDraftDiaries(final Long userId);
    DraftDiaryResDetailDTO getDraftDiaryById(final Long userId, final Long diaryId);
}
