package moodbuddy.moodbuddy.domain.draftDiary.service;

import moodbuddy.moodbuddy.domain.draftDiary.domain.DraftDiary;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqPublishDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;

import java.time.LocalDate;
import java.util.List;

public interface DraftDiaryService {
    DraftDiary saveDraftDiary(DraftDiaryReqSaveDTO diaryReqSaveDTO, final Long userId);
    Long publishDraftDiary(DraftDiaryReqPublishDTO requestDTO, final Long userId);
    List<DraftDiaryResFindOneDTO> getDraftDiaries(final Long userId);
    void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO, final Long userId);
    DraftDiaryResDetailDTO getDraftDiary(final Long diaryId, final Long userId);
    void deleteTodayDraftDiaries(Long userId, LocalDate draftDiaryDate);
}