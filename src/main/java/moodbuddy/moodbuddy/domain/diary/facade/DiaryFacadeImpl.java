package moodbuddy.moodbuddy.domain.diary.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.service.BookMarkService;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.diary.service.image.DiaryImageService;
import moodbuddy.moodbuddy.domain.diary.service.DiaryService;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.service.DiaryDocumentService;
import moodbuddy.moodbuddy.domain.user.service.UserService;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import moodbuddy.moodbuddy.global.common.redis.service.RedisService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DiaryFacadeImpl implements DiaryFacade {
    private final DiaryService diaryService;
    private final DiaryDocumentService diaryDocumentService;
    private final DiaryImageService diaryImageService;
    private final BookMarkService bookMarkService;
    private final UserService userService;
    private final GptService gptService;
    private final RedisService redisService;

    @Override
    @Transactional
    public DiaryResSaveDTO saveDiary(DiaryReqSaveDTO requestDTO) {
        //TODO 일기 저장, 이미지 저장, 일라스틱서치 저장 분리할 필요가 있음.
        final var userId = JwtUtil.getUserId();
        diaryService.validateExistingDiary(requestDTO.diaryDate(), userId);
        var diary = diaryService.saveDiary(requestDTO, userId);
        if(requestDTO.diaryImageUrls() != null) {
            diaryImageService.saveAll(requestDTO.diaryImageUrls(), diary.getId());
        }
        diaryDocumentService.save(diary);
        checkTodayDiary(requestDTO.diaryDate(), userId, false);
        redisService.deleteDiaryCaches(userId);
        log.info("[일기 저장] saveDiary(): diaryId: {}, userId: {}", diary.getId(), userId);
        return new DiaryResSaveDTO(diary.getId());
    }

    @Override
    @Transactional
    public DiaryResSaveDTO updateDiary(DiaryReqUpdateDTO requestDTO) {
        final var userId = JwtUtil.getUserId();
        var diary = diaryService.updateDiary(requestDTO, userId);
        diaryImageService.deleteAll(diary.getId());
        if(requestDTO.newImageUrls() != null) {
            diaryImageService.saveAll(requestDTO.newImageUrls(), diary.getId());
        }
        diaryDocumentService.save(diary);
        redisService.deleteDiaryCaches(userId);
        log.info("[일기 수정] updateDiary(): diaryId: {}, userId: {}", diary.getId(), userId);
        return new DiaryResSaveDTO(diary.getId());
    }

    @Override
    @Transactional
    public void deleteDiary(final Long diaryId) {
        final var userId = JwtUtil.getUserId();
        var findDiary = diaryService.deleteDiary(diaryId, userId);
        bookMarkService.deleteByDiaryId(diaryId);
        diaryImageService.deleteAll(diaryId);
//        diaryDocumentService.delete(diaryId);
        checkTodayDiary(findDiary.getDate(), userId, true);
        log.info("[일기 삭제] deleteDiary(): diaryId: {}, userId: {}", diaryId, userId);
        redisService.deleteDiaryCaches(userId);
    }

    @Override
    public DiaryResDetailDTO getDiary(final Long diaryId) {
        final var userId = JwtUtil.getUserId();
        return diaryService.getDiary(diaryId, userId);
    }

    @Override
    public void analyze(Long diaryId) {
        final var userId = JwtUtil.getUserId();
        diaryService.getDiary(diaryId, userId);
    }

    private void checkTodayDiary(LocalDate diaryDate, Long userId, boolean check) {
        var today = LocalDate.now();
        if (diaryDate.isEqual(today)) {
            userService.changeCount(userId, check);
            userService.setUserCheckTodayDairy(userId, check);
        }
    }
}
