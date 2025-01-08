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
    DraftDiary saveDraftDiary(final Long userId, DraftDiaryReqSaveDTO diaryReqSaveDTO);
    Long publishDraftDiary(final Long userId, DraftDiaryReqPublishDTO requestDTO);
    List<DraftDiaryResFindOneDTO> getDraftDiaries(final Long userId);
    void deleteDraftDiaries(final Long userId, DraftDiaryReqSelectDeleteDTO requestDTO);
    DraftDiaryResDetailDTO getDraftDiary(final Long userId,  final Long diaryId);
    void deleteTodayDraftDiaries(Long userId, LocalDate draftDiaryDate);
}