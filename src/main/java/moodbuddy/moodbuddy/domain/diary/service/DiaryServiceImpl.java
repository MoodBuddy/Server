package moodbuddy.moodbuddy.domain.diary.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
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
    public Diary saveDiary(DiaryReqSaveDTO requestDTO, final Long userId) {
        deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
        return diaryRepository.save(Diary.of(
                requestDTO,
                userId));
    }

    @Override
    @Transactional
    public Diary updateDiary(DiaryReqUpdateDTO requestDTO, final Long userId) {
        try {
            Diary findDiary = findDiaryById(requestDTO.diaryId());
            validateDiaryAccess(findDiary, userId);
            findDiary.updateDiary(requestDTO);
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
    @Cacheable(cacheNames = "getDiary", key = "'userId:'+#userId+'_'+'diaryId:'+#diaryId", unless = "#result == null")
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
        if (diaryRepository.findByDateAndUserId(diaryDate, userId).isPresent()) {
            throw new DiaryTodayExistingException(ErrorCode.DIARY_TODAY_EXISTING);
        }
    }

    @Override
    public Diary findDiaryById(Long diaryId) {
        return diaryRepository.findByIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(DIARY_NOT_FOUND));
    }

    private void deleteTodayDraftDiaries(LocalDate diaryDate, Long userId) {
        diaryRepository.findAllByDateAndUserId(diaryDate, userId)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }
}
