package moodbuddy.moodbuddy.domain.diary.facade.draft;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.mapper.DiaryMapper;
import moodbuddy.moodbuddy.domain.diary.service.draft.DraftDiaryService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DraftDiaryFacadeImpl implements DraftDiaryFacade {
    private final DraftDiaryService draftDiaryService;
    private final DiaryMapper diaryMapper;

    @Override
    @Transactional
    public DiaryResDetailDTO saveDraftDiary(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.draftSave(requestDTO, userId);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    public DiaryResDraftFindAllDTO draftFindAll() {
        final Long userId = JwtUtil.getUserId();
        return draftDiaryService.draftFindAll(userId);
    }

    @Override
    @Transactional
    public void draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        draftDiaryService.draftSelectDelete(requestDTO, userId);
    }
}
