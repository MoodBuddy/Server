package moodbuddy.moodbuddy.domain.diary.service.draft;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import java.util.List;
import java.util.Map;

public interface DraftDiaryService {
    Diary saveDraftDiary(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);
    Diary updateDraftDiary(DiaryReqUpdateDTO requestDTO, Map<String, String> stringStringMap, final Long userId);
    List<DraftDiaryResFindOneDTO> findAll(final Long userId);
    void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO, final Long userId);
    DraftDiaryResDetailDTO getDraftDiary(final Long diaryId, final Long userId);
}