package moodbuddy.moodbuddy.domain.diary.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNoAccessException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryTodayExistingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.DIARY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final String DIARY_SUMMARY = "summary";
    private final String DIARY_SUBJECT = "subject";

    @Override
    @Transactional
    public Diary saveDiary(DiaryReqSaveDTO requestDTO, Map<String, String> gptResults, final Long userId) {
        Diary diary = diaryRepository.save(Diary.ofPublished(
                requestDTO,
                userId,
                gptResults.get(DIARY_SUMMARY),
                DiarySubject.valueOf(gptResults.get(DIARY_SUBJECT))
        ));
        deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
        return diary;
    }

    @Override
    @Transactional
    public Diary updateDiary(DiaryReqUpdateDTO requestDTO, Map<String, String> gptResults, final Long userId) {
        try {
            Diary findDiary = findDiaryById(requestDTO.diaryId());
            validateDiaryAccess(findDiary, userId);
            findDiary.updateDiary(requestDTO, gptResults);
            deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
            return findDiary;
        } catch (OptimisticLockException ex) {
            throw new DiaryConcurrentUpdateException(ErrorCode.DIARY_CONCURRENT_UPDATE);
        }
    }

    @Override
    @Transactional
    public Diary deleteDiary(Long diaryId, Long userId) {
        final Diary findDiary = findDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        findDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        return findDiary;
    }

    @Override
    //TODO 캐싱 붙여야 함
    public DiaryResDetailDTO getDiary(final Long diaryId, final Long userId) {
        final Diary findDiary = findDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        return diaryRepository.getDiaryById(diaryId);
    }

    @Override
    public void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.DIARY_NO_ACCESS);
        }
    }

    @Override
    public void validateExistingDiary(LocalDate diaryDate, Long userId) {
        if (diaryRepository.findByDateAndUserIdAndStatus(diaryDate, userId, DiaryStatus.PUBLISHED).isPresent()) {
            throw new DiaryTodayExistingException(ErrorCode.DIARY_TODAY_EXISTING);
        }
    }

    @Override
    public Diary findDiaryById(Long diaryId) {
        return diaryRepository.findByIdAndStatusAndMoodBuddyStatus(diaryId, DiaryStatus.PUBLISHED, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(DIARY_NOT_FOUND));
    }

    private void deleteTodayDraftDiaries(LocalDate diaryDate, Long userId) {
        diaryRepository.findAllByDateAndUserIdAndStatus(diaryDate, userId, DiaryStatus.DRAFT)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }
}
