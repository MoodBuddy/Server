package moodbuddy.moodbuddy.domain.diary.service.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.repository.base.DiaryRepository;
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
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final String DIARY_SUMMARY = "summary";
    private final String DIARY_SUBJECT = "subject";

    @Override
    @Transactional
    public Diary save(DiaryReqSaveDTO requestDTO, Map<String, String> gptResults, final Long userId) {
        validateExistingDiary(requestDTO.diaryDate(), userId);
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
        Diary findDiary = findDiaryById(requestDTO.diaryId());
        validateDiaryAccess(findDiary, userId);
        findDiary.updateDiary(requestDTO, gptResults);
        deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
        return findDiary;
    }

    @Override
    @Transactional
    public Diary delete(Long diaryId, Long userId) {
        final Diary findDiary = findDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        findDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        return findDiary;
    }

    @Override
    @Transactional
    public Diary draftSave(DiaryReqSaveDTO requestDTO, final Long userId) {
       return diaryRepository.save(diaryRepository.save(Diary.ofDraft(
               requestDTO,
                userId)));
    }

    @Override
    public DiaryResDraftFindAllDTO draftFindAll(final Long userId) {
        return diaryRepository.draftFindAllByUserId(userId);
    }

    @Override
    @Transactional
    public List<Diary> draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO, final Long userId) {
        List<Diary> diaries = diaryRepository.findAllById(requestDTO.diaryIdList());
        for (Diary diary : diaries) {
            diary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        }
        return diaries;
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
        List<Diary> draftDiaries = diaryRepository.findAllByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.DRAFT);
        if (!draftDiaries.isEmpty()) {
            for(Diary draftDiary : draftDiaries) {
                draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
            }
        }
    }

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException(NOT_FOUND_DIARY));
    }
}
