package moodbuddy.moodbuddy.domain.diary.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryNoAccessException;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryNotFoundException;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryTodayExistingException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import static moodbuddy.moodbuddy.global.error.ErrorCode.DIARY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public Long saveDiary(final Long userId, DiaryReqSaveDTO requestDTO) {
        return diaryRepository.save(Diary.of(
                requestDTO,
                userId)).getId();
    }

    @Override
    @Transactional
    public Long updateDiary(final Long userId,  DiaryReqUpdateDTO requestDTO) {
        try {
            var findDiary = findDiaryById(requestDTO.diaryId());
            validateDiaryAccess(userId, findDiary);
            findDiary.updateDiary(requestDTO);
            return findDiary.getId();
        } catch (OptimisticLockException ex) {
            throw new DiaryConcurrentUpdateException(ErrorCode.DIARY_CONCURRENT_UPDATE);
        }
    }

    @Override
    @Transactional
    public LocalDate deleteDiary(final Long userId,  final Long diaryId) {
        final var findDiary = findDiaryById(diaryId);
        validateDiaryAccess(userId, findDiary);
        findDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        return findDiary.getDate();
    }

    @Override
    @Cacheable(cacheNames = "getDiary", key = "'userId:'+#userId+'_'+'diaryId:'+#diaryId", unless = "#result == null")
    public DiaryResDetailDTO getDiary(final Long userId, final Long diaryId) {
        final Diary findDiary = findDiaryById(diaryId);
        validateDiaryAccess(userId, findDiary);
        return diaryRepository.getDiaryById(diaryId);
    }

    @Override
    public void validateDiaryAccess(final Long userId,  Diary findDiary) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.DIARY_NO_ACCESS);
        }
    }

    @Override
    public void validateExistingDiary(final Long userId,  LocalDate diaryDate) {
        if (diaryRepository.findByUserIdAndDate(userId, diaryDate).isPresent()) {
            throw new DiaryTodayExistingException(ErrorCode.DIARY_TODAY_EXISTING);
        }
    }

    @Override
    public Diary findDiaryById(final Long diaryId) {
        return diaryRepository.findByIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(DIARY_NOT_FOUND));
    }
}
