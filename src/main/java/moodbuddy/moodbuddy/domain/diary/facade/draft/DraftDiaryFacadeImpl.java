package moodbuddy.moodbuddy.domain.diary.facade.draft;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.mapper.DiaryMapper;
import moodbuddy.moodbuddy.domain.diary.mapper.draft.DraftDiaryMapper;
import moodbuddy.moodbuddy.domain.diary.service.DiaryService;
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
    private final DraftDiaryMapper draftDiaryMapper;

    @Override
    @Transactional
    public DiaryResDetailDTO save(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.save(requestDTO, userId);
        if(requestDTO.diaryImageURLs() != null) {
            diaryImageService.saveAll(requestDTO.diaryImageURLs(), diary.getDiaryId());
        }
        return draftDiaryMapper.toResDetailDTO(diary);
    }

    @Override
    @Transactional
    public DiaryResDetailDTO update(DiaryReqUpdateDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.update(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryImageService.deleteAll(diary.getDiaryId());
        if(requestDTO.newImageURLs() != null) {
            diaryImageService.saveAll(requestDTO.newImageURLs(), diary.getDiaryId());
        }
        diaryDocumentService.save(diary);
        return draftDiaryMapper.toResDetailDTO(diary);
    }

    @Override
    public List<DraftDiaryResFindOneDTO> findAll() {
        final Long userId = JwtUtil.getUserId();
        return draftDiaryService.findAll(userId);
    }

    @Override
    @Transactional
    public void selectDelete(DraftDiaryReqSelectDeleteDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        draftDiaryService.selectDelete(requestDTO, userId);
    }

    @Override
    public DraftDiaryResDetailDTO findOneByDiaryId(Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        return draftDiaryService.findOneByDiaryId(diaryId, userId);
    }
}
