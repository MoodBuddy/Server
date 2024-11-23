package moodbuddy.moodbuddy.domain.diary.service;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNoAccessException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryTodayExistingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.NOT_FOUND_DIARY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final String DIARY_SUMMARY = "summary";
    private final String DIARY_SUBJECT = "subject";

    @Override
    @Transactional
    public Diary save(DiaryReqSaveDTO requestDTO, Map<String, String> gptResults, final Long userId) {
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
    public Diary update(DiaryReqUpdateDTO requestDTO, Map<String, String> gptResults, final Long userId) {
        Diary findDiary = getDiaryById(requestDTO.diaryId());
        validateDiaryAccess(findDiary, userId);
        findDiary.updateDiary(requestDTO, gptResults);
        deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
        return findDiary;
    }

    @Override
    @Transactional
    public Diary delete(Long diaryId, Long userId) {
        final Diary findDiary = getDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        findDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        return findDiary;
    }

    @Override
    public DiaryResDetailDTO findOneByDiaryId(final Long diaryId, final Long userId) {
        final Diary findDiary = getDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        return diaryRepository.findOneByDiaryId(diaryId);
    }

    @Override
    public void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.NO_ACCESS_DIARY);
        }
    }

    @Override
    public void validateExistingDiary(LocalDate diaryDate, Long userId) {
        if (diaryRepository.findByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.PUBLISHED).isPresent()) {
            throw new DiaryTodayExistingException(ErrorCode.TODAY_EXISTING_DIARY);
        }
    }

    private void deleteTodayDraftDiaries(LocalDate diaryDate, Long userId) {
        diaryRepository.findAllByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.DRAFT)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }

    @Override
    public Diary getDiaryById(Long diaryId) {
        return diaryRepository.findByDiaryIdAndDiaryStatusAndMoodBuddyStatus(diaryId, DiaryStatus.PUBLISHED, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(NOT_FOUND_DIARY));
    }
}
