package moodbuddy.moodbuddy.domain.diary.service.diary;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DiaryService {
    // 일기 저장
    Diary save(DiaryReqSaveDTO diaryReqSaveDTO, Map<String, String> gptResults, final Long userId);

    // 일기 수정
    Diary update(DiaryReqUpdateDTO diaryReqUpdateDTO, Map<String, String> gptResults, final Long userId);

    // 일기 삭제
    Diary delete(Long diaryId, Long userId);

    // 일기 임시 저장
    Diary draftSave(DiaryReqSaveDTO diaryReqSaveDTO, final Long userId);

    // 일기 임시 저장 날짜 조회
    DiaryResDraftFindAllDTO draftFindAll(final Long userId);

    // 일기 임시 저장 선택 삭제
    List<Diary> draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO, final Long userId);

    // 일기 하나 조회
    DiaryResDetailDTO findOneByDiaryId(final Long diaryId, final Long userId);

    // 일기 전체 조회 (페이징)
    Page<DiaryResDetailDTO> findAll(Pageable pageable, final Long userId);

    // 일기 비슷한 감정 조회
    Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId);

    // 상세검색 조회
    Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId);

    // 일기 조회
    Diary findDiaryById(Long diaryId);
    void validateDiaryAccess(Diary findDiary, Long userId);
}
