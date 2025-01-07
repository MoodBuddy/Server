package moodbuddy.moodbuddy.domain.draftDiary.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.draftDiary.domain.DraftDiary;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.service.DraftDiaryService;
import moodbuddy.moodbuddy.domain.diary.service.image.DiaryImageService;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.service.DiaryDocumentService;
import moodbuddy.moodbuddy.global.common.redis.service.RedisService;
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
    private final RedisService redisService;

    @Override
    @Transactional
    public DraftDiaryResSaveDTO saveDraftDiary(DraftDiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        DraftDiary draftDiary = draftDiaryService.saveDraftDiary(requestDTO, userId);
        if(requestDTO.diaryImageUrls() != null) {
            diaryImageService.saveAll(requestDTO.diaryImageUrls(), draftDiary.getId());
        } // TODO 해결해야 함
        return new DraftDiaryResSaveDTO(draftDiary.getId());
    }

    @Override
    @Transactional
    public DiaryResSaveDTO updateDraftDiary(DiaryReqUpdateDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = draftDiaryService.updateDraftDiary(requestDTO, userId);
        diaryImageService.deleteAll(diary.getId());
        if(requestDTO.diaryImageUrls() != null) {
            diaryImageService.saveAll(requestDTO.diaryImageUrls(), diary.getId());
        }
        diaryDocumentService.save(diary);
        redisService.deleteDiaryCaches(userId);
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
