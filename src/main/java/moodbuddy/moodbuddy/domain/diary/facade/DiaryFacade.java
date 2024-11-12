package moodbuddy.moodbuddy.domain.diary.facade;

import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryFacade {
    DiaryResDetailDTO saveDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDetailDTO updateDiary(DiaryReqUpdateDTO requestDTO);
    void deleteDiary(final Long diaryId);
    DiaryResDetailDTO saveDraftDiary(DiaryReqSaveDTO requestDTO);
    DiaryResDraftFindAllDTO draftFindAll();
    void draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO);
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId);
    Page<DiaryResDetailDTO> findAll(Pageable pageable);
    Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable);
    Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable);
}
