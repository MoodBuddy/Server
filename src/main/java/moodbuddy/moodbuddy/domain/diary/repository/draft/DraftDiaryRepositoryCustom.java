package moodbuddy.moodbuddy.domain.diary.repository.draft;

import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;

import java.util.List;

public interface DraftDiaryRepositoryCustom {
    List<DraftDiaryResFindOneDTO> getDraftDiaries(Long userId);
    DraftDiaryResDetailDTO getDraftDiaryById(Long diaryId);
}
