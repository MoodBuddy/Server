package moodbuddy.moodbuddy.domain.draftDiary.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.draftDiary.repository.DraftDiaryRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.*;
import moodbuddy.moodbuddy.global.common.exception.diary.draft.DraftDiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.global.common.exception.diary.draft.DraftDiaryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.DRAFT_DIARY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftDiaryServiceImpl implements DraftDiaryService {
    private final DraftDiaryRepository draftDiaryRepository;

    @Override
    @Transactional
    public Diary saveDraftDiary(DiaryReqSaveDTO requestDTO, final Long userId) {
        return draftDiaryRepository.save((Diary.ofDraft(
                requestDTO,
                userId)));
    }

    @Override
    @Transactional
    public Diary updateDraftDiary(DiaryReqUpdateDTO requestDTO, Long userId) {
        try {
            Diary findDiary = findDraftDiaryById(requestDTO.diaryId());
            validateDiaryAccess(findDiary, userId);
            findDiary.updateDiary(requestDTO);
            deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
            return findDiary;
        } catch (OptimisticLockException ex) {
            throw new DraftDiaryConcurrentUpdateException(ErrorCode.DRAFT_DIARY_CONCURRENT_UPDATE);
        }
    }

    @Override
    public List<DraftDiaryResFindOneDTO> getDraftDiaries(final Long userId) {
        return draftDiaryRepository.getDraftDiaries(userId);
    }

    @Override
    @Transactional
    public void deleteDraftDiaries(DraftDiaryReqSelectDeleteDTO requestDTO, Long userId) {
        requestDTO.diaryIdList().forEach(diaryId ->
                findDraftDiaryById(diaryId).updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE)
        );
    }

    @Override
    public DraftDiaryResDetailDTO getDraftDiary(Long diaryId, Long userId) {
        final Diary findDiary = findDraftDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        return draftDiaryRepository.getDraftDiaryById(diaryId);
    }

    private void deleteTodayDraftDiaries(LocalDate diaryDate, Long userId) {
        draftDiaryRepository.findAllByDateAndUserIdAndStatus(diaryDate, userId, DiaryStatus.DRAFT)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }

    private Diary findDraftDiaryById(Long diaryId) {
        return draftDiaryRepository.findByIdAndStatusAndMoodBuddyStatus(diaryId, DiaryStatus.DRAFT, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DraftDiaryNotFoundException(DRAFT_DIARY_NOT_FOUND));
    }

    private void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.DIARY_NO_ACCESS);
        }
    }
}
