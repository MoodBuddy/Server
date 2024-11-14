package moodbuddy.moodbuddy.domain.diary.facade.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.service.BookMarkService;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.mapper.DiaryMapper;
import moodbuddy.moodbuddy.domain.diary.service.image.DiaryImageService;
import moodbuddy.moodbuddy.domain.diary.service.base.DiaryService;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.service.DiaryDocumentService;
import moodbuddy.moodbuddy.domain.user.service.UserService;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryFacadeImpl implements DiaryFacade {
    private final DiaryService diaryService;
    private final DiaryDocumentService diaryDocumentService;
    private final DiaryImageService diaryImageService;
    private final BookMarkService bookMarkService;
    private final UserService userService;
    private final GptService gptService;
    private final DiaryMapper diaryMapper;

    @Override
    @Transactional
    public DiaryResDetailDTO saveDiary(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        diaryService.validateExistingDiary(requestDTO.diaryDate(), userId);
        Diary diary = diaryService.save(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryDocumentService.save(diary);
        checkTodayDiary(requestDTO.diaryDate(), userId, false);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    @Transactional
    public DiaryResDetailDTO updateDiary(DiaryReqUpdateDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = diaryService.update(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryDocumentService.save(diary);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    @Transactional
    public void deleteDiary(final Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        Diary findDiary = diaryService.delete(diaryId, userId);
        bookMarkService.deleteByDiaryId(diaryId);
        diaryImageService.deleteAllDiaryImages(findDiary);
        diaryDocumentService.delete(diaryId);
        checkTodayDiary(findDiary.getDiaryDate(), userId, true);
    }

    //TODO 이미지 로직 구현해야 함
    @Transactional
    public DiaryResDetailDTO saveImage(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = diaryService.save(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryDocumentService.save(diary);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Transactional
    public DiaryResDetailDTO updateImage(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = diaryService.save(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryDocumentService.save(diary);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    @Transactional
    public DiaryResDetailDTO saveDraftDiary(DiaryReqSaveDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = diaryService.draftSave(requestDTO, userId);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    public DiaryResDraftFindAllDTO draftFindAll() {
        final Long userId = JwtUtil.getUserId();
        return diaryService.draftFindAll(userId);
    }

    @Override
    @Transactional
    public void draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        diaryService.draftSelectDelete(requestDTO, userId);
    }

    private void checkTodayDiary(LocalDate diaryDate, Long userId, boolean check) {
        LocalDate today = LocalDate.now();
        if (diaryDate.isEqual(today)) {
            userService.changeCount(userId, check);
            userService.setUserCheckTodayDairy(userId, check);
        }
    }
}
