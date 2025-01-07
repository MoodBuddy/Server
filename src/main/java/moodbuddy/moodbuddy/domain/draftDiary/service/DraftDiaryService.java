package moodbuddy.moodbuddy.domain.draftDiary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import java.util.List;

public interface DraftDiaryService {
    Diary saveDraftDiary(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);
    Diary updateDraftDiary(DiaryReqUpdateDTO requestDTO, final Long userId);
    List<DraftDiaryResFindOneDTO> getDraftDiaries(final Long userId);
    void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO, final Long userId);
    DraftDiaryResDetailDTO getDraftDiary(final Long diaryId, final Long userId);
}