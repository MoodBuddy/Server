package moodbuddy.moodbuddy.domain.diary.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.bookMark.service.BookMarkService;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.mapper.DiaryMapper;
import moodbuddy.moodbuddy.domain.diary.service.image.DiaryImageService;
import moodbuddy.moodbuddy.domain.diary.service.DiaryService;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.service.DiaryDocumentService;
import moodbuddy.moodbuddy.domain.user.service.UserService;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

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
    public DiaryResDetailDTO save(DiaryReqSaveDTO requestDTO) {
        //TODO 일기 저장, 이미지 저장, 일라스틱서치 저장 분리할 필요가 있음.
        final Long userId = JwtUtil.getUserId();
        diaryService.validateExistingDiary(requestDTO.diaryDate(), userId);
        Diary diary = diaryService.save(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        if(requestDTO.diaryImageURLs() != null) {
            diaryImageService.saveAll(requestDTO.diaryImageURLs(), diary.getDiaryId());
        }
        diaryDocumentService.save(diary);
        checkTodayDiary(requestDTO.diaryDate(), userId, false);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    @Transactional
    public DiaryResDetailDTO update(DiaryReqUpdateDTO requestDTO) {
        final Long userId = JwtUtil.getUserId();
        Diary diary = diaryService.update(requestDTO, gptService.analyzeDiaryContent(requestDTO.diaryContent()), userId);
        diaryImageService.deleteAll(diary.getDiaryId());
        if(requestDTO.newImageURLs() != null) {
            diaryImageService.saveAll(requestDTO.newImageURLs(), diary.getDiaryId());
        }
        diaryDocumentService.save(diary);
        return diaryMapper.toResDetailDTO(diary);
    }

    @Override
    @Transactional
    public void delete(final Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        Diary findDiary = diaryService.delete(diaryId, userId);
        bookMarkService.deleteByDiaryId(diaryId);
        diaryImageService.deleteAll(diaryId);
//        diaryDocumentService.delete(diaryId);
        checkTodayDiary(findDiary.getDiaryDate(), userId, true);
    }

    @Override
    public DiaryResDetailDTO findOneByDiaryId(final Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        return diaryService.findOneByDiaryId(diaryId, userId);
    }

    private void checkTodayDiary(LocalDate diaryDate, Long userId, boolean check) {
        LocalDate today = LocalDate.now();
        if (diaryDate.isEqual(today)) {
            userService.changeCount(userId, check);
            userService.setUserCheckTodayDairy(userId, check);
        }
    }
}
