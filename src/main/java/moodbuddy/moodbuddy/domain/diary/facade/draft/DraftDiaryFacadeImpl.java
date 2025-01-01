package moodbuddy.moodbuddy.domain.diary.facade.draft;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.diary.service.draft.DraftDiaryService;
import moodbuddy.moodbuddy.domain.diary.service.image.DiaryImageService;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.service.DiaryDocumentService;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DraftDiaryFacadeImpl implements DraftDiaryFacade {
    private final DraftDiaryService draftDiaryService;
    private final DiaryDocumentService diaryDocumentService;
    private final DiaryImageService diaryImageService;
    private final GptService gptService;

    @Override
    @Transactional
    public DiaryResSaveDTO saveDraftDiary(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.saveDraftDiary(requestDTO, userId);
        if(requestDTO.diaryImageUrls() != null) {
            diaryImageService.saveAll(requestDTO.diaryImageUrls(), diary.getId());
        }
        return new DiaryResSaveDTO(diary.getId());
    }

    @Override
    @Transactional
    public DiaryResSaveDTO updateDraftDiary(DiaryReqUpdateDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.updateDraftDiary(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryImageService.deleteAll(diary.getId());
        if(requestDTO.newImageUrls() != null) {
            diaryImageService.saveAll(requestDTO.newImageUrls(), diary.getId());
        }
        diaryDocumentService.save(diary);
        return new DiaryResSaveDTO(diary.getId());
    }

    @Override
    public List<DraftDiaryResFindOneDTO> getDraftDiaries() {
        final Long userId = JwtUtil.getUserId();
        return draftDiaryService.getDraftDiaries(userId);
    }

    @Override
    @Transactional
    public void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        draftDiaryService.deleteDraftDiaries(requestDTO, userId);
    }

    @Override
    public DraftDiaryResDetailDTO getDraftDiary(Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        return draftDiaryService.getDraftDiary(diaryId, userId);
    }
}
