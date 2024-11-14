package moodbuddy.moodbuddy.domain.diary.repository.draft;

import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;

public interface DraftDiaryRepositoryCustom {
    DiaryResDraftFindAllDTO draftFindAllByUserId(Long userId);
}
