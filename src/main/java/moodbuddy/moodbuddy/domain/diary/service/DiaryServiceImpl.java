package moodbuddy.moodbuddy.domain.diary.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.service.BookMarkService;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.mapper.DiaryMapper;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.global.common.elasticSearch.mapper.DiaryDocumentMapper;
import moodbuddy.moodbuddy.global.common.elasticSearch.repository.DiaryDocumentRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryTodayExistingException;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import moodbuddy.moodbuddy.domain.user.service.UserService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryDocumentRepository diaryDocumentRepository;
    private final DiaryImageService diaryImageService;
    private final DiaryFindService diaryFindService;
    private final BookMarkService bookMarkService;
    private final UserService userService;
    private final GptService gptService;

    @Override
    @Transactional
    public DiaryResDetailDTO save(DiaryReqSaveDTO diaryReqSaveDTO) throws IOException {
        final Long userId = JwtUtil.getUserId();

        validateExistingDiary(diaryRepository, diaryReqSaveDTO.diaryDate(), userId);

        Diary diary = DiaryMapper.toDiaryEntity(
                diaryReqSaveDTO,
                userId,
                gptService.analyzeDiaryContent(diaryReqSaveDTO.diaryContent()).get("summary"),
                DiarySubject.valueOf(gptService.analyzeDiaryContent(diaryReqSaveDTO.diaryContent()).get("subject"))
        );

        diary = diaryRepository.save(diary);

        // Elasticsearch에서 문서 저장
        diaryDocumentRepository.save(DiaryDocumentMapper.toDiaryDocument(diary));

        // TODO 일기 내용 저장, 이미지 저장 API 분리하기

        diaryImageService.saveDiaryImages(diaryReqSaveDTO.diaryImgList(), diary);

        checkTodayDiary(diaryReqSaveDTO.diaryDate(), userId, false);
        deleteDraftDiaries(diaryReqSaveDTO.diaryDate(), userId);

        return DiaryMapper.toDetailDTO(diary);
    }

    @Override
    @Transactional
    public DiaryResDetailDTO update(DiaryReqUpdateDTO diaryReqUpdateDTO) throws IOException {
        final Long userId = JwtUtil.getUserId();

        if (isDraftToPublished(diaryReqUpdateDTO)) {
            validateExistingDiary(diaryRepository, diaryReqUpdateDTO.diaryDate(), userId);
            checkTodayDiary(diaryReqUpdateDTO.diaryDate(), userId, false);
        }

        Diary findDiary = diaryFindService.findDiaryById(diaryReqUpdateDTO.diaryId());
        diaryFindService.validateDiaryAccess(findDiary, userId);

        String summary = gptService.analyzeDiaryContent(diaryReqUpdateDTO.diaryContent()).get("summary");
        DiarySubject diarySubject = DiarySubject.valueOf(gptService.analyzeDiaryContent(diaryReqUpdateDTO.diaryContent()).get("subject"));

        findDiary.updateDiary(diaryReqUpdateDTO, summary, diarySubject);

        // 기존 이미지 삭제
        diaryImageService.deleteExcludingImages(findDiary, diaryReqUpdateDTO.existingDiaryImgList());
        // 새로운 이미지 저장
        diaryImageService.saveDiaryImages(diaryReqUpdateDTO.diaryImgList(), findDiary);

        deleteDraftDiaries(diaryReqUpdateDTO.diaryDate(), userId);

        // Elasticsearch에서 문서 수정
        diaryDocumentRepository.save(DiaryDocumentMapper.toDiaryDocument(findDiary));

        return DiaryMapper.toDetailDTO(findDiary);
    }

    @Override
    @Transactional
    public void delete(Long diaryId) throws IOException {
        final Long userId = JwtUtil.getUserId();
        final Diary findDiary = diaryFindService.findDiaryById(diaryId);
        diaryFindService.validateDiaryAccess(findDiary, userId);

        checkTodayDiary(findDiary.getDiaryDate(), userId, true);

        bookMarkService.deleteByDiaryId(diaryId);
        diaryImageService.deleteAllDiaryImages(findDiary);

        // Elasticsearch에서 문서 삭제
        diaryDocumentRepository.deleteById(diaryId);
        diaryRepository.delete(findDiary);
    }

    @Override
    @Transactional
    public DiaryResDetailDTO draftSave(DiaryReqSaveDTO diaryReqSaveDTO) throws IOException {
        final Long userId = JwtUtil.getUserId();

        Diary diary = DiaryMapper.toDraftEntity(diaryReqSaveDTO, userId);
        diary = diaryRepository.save(diary);

        diaryImageService.saveDiaryImages(diaryReqSaveDTO.diaryImgList(), diary);

        return DiaryMapper.toDetailDTO(diary);
    }

    @Override
    public DiaryResDraftFindAllDTO draftFindAll() {
        final Long userId = JwtUtil.getUserId();
        return diaryRepository.draftFindAllByUserId(userId);
    }

    @Override
    @Transactional
    public void draftSelectDelete(DiaryReqDraftSelectDeleteDTO diaryReqDraftSelectDeleteDTO) {
        final Long userId = JwtUtil.getUserId();

        List<Diary> diariesToDelete = diaryRepository.findAllById(diaryReqDraftSelectDeleteDTO.diaryIdList()).stream()
                .peek(diary -> diaryFindService.validateDiaryAccess(diary, userId)) // 접근 권한 확인
                .collect(Collectors.toList());

        diariesToDelete.forEach(diary -> {
            try {
                diaryImageService.deleteAllDiaryImages(diary);
            } catch (IOException e) {
                log.error("Error deleting diary images", e);
                throw new RuntimeException(e);  // 필요에 따라 적절한 예외 처리
            }
        });

        diaryRepository.deleteAll(diariesToDelete);
    }


    @Override
    public DiaryResDetailDTO findOneByDiaryId(Long diaryId) {
        final Long userId = JwtUtil.getUserId();

        final Diary findDiary = diaryFindService.findDiaryById(diaryId);
        diaryFindService.validateDiaryAccess(findDiary, userId);

        return diaryRepository.findOneByDiaryId(diaryId);
    }

    @Override
    public Page<DiaryResDetailDTO> findAll(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();

        return diaryRepository.findAllByUserIdWithPageable(userId, pageable);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();

        return diaryRepository.findAllByEmotionWithPageable(diaryEmotion, userId, pageable);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO diaryReqFilterDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();

        return diaryRepository.findAllByFilterWithPageable(diaryReqFilterDTO, userId, pageable);
    }

    private boolean isDraftToPublished(DiaryReqUpdateDTO diaryReqUpdateDTO) {
        return diaryReqUpdateDTO.diaryStatus().equals(DiaryStatus.DRAFT);
    }

    private void checkTodayDiary(LocalDate diaryDate, Long userId, boolean check) {
        LocalDate today = LocalDate.now();
        if (diaryDate.isEqual(today)) {
            userService.changeCount(userId, check); // 일기 작성하면 편지지 개수 늘려주기
            userService.setUserCheckTodayDairy(userId, check); // 일기 작성 불가
        }
    }
    private void deleteDraftDiaries(LocalDate diaryDate, Long userId) {
        List<Diary> draftDiaries = diaryRepository.findAllByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.DRAFT);
        if (!draftDiaries.isEmpty()) {
            diaryRepository.deleteAll(draftDiaries);
        }
    }

    private void validateExistingDiary(DiaryRepository diaryRepository, LocalDate diaryDate, Long userId) {
        if (diaryRepository.findByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.PUBLISHED).isPresent()) {
            throw new DiaryTodayExistingException(ErrorCode.TODAY_EXISTING_DIARY);
        }
    }
}
