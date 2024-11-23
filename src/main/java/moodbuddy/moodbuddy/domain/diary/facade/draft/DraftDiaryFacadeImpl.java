package moodbuddy.moodbuddy.domain.diary.facade.draft;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DiaryReqDraftSelectDeleteDTO;
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

    //TODO 이미지 관리 해결해야 함.
    @Override
    @Transactional
    public DiaryResDetailDTO save(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.save(requestDTO, userId);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    public DiaryResDraftFindAllDTO findAll() {
        final Long userId = JwtUtil.getUserId();
        return draftDiaryService.findAll(userId);
    }

    @Override
    @Transactional
    public void selectDelete(DiaryReqDraftSelectDeleteDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        draftDiaryService.selectDelete(requestDTO, userId);
    }

    @Override
    public DiaryResDetailDTO findOneByDiaryId(Long diaryId) {
        return null;
    }
}
