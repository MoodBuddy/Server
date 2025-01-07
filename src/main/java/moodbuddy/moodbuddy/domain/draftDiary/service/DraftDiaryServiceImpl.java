package moodbuddy.moodbuddy.domain.draftDiary.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.domain.draftDiary.domain.DraftDiary;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqPublishDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.response.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.draftDiary.repository.DraftDiaryRepository;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryTodayExistingException;
import moodbuddy.moodbuddy.global.common.exception.diary.draft.DraftDiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.global.common.exception.diary.draft.DraftDiaryNotFoundException;
import moodbuddy.moodbuddy.global.common.exception.draftDiary.DraftDiaryNoAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.DRAFT_DIARY_NO_ACCESS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftDiaryServiceImpl implements DraftDiaryService {
    private final DraftDiaryRepository draftDiaryRepository;
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public DraftDiary saveDraftDiary(DraftDiaryReqSaveDTO requestDTO, final Long userId) {
        return draftDiaryRepository.save((DraftDiary.of(
                requestDTO,
                userId)));
    }

    @Override
    @Transactional
    public Long publishDraftDiary(DraftDiaryReqPublishDTO requestDTO, Long userId) {
        try {
            var draftDiary = findDraftDiaryById(requestDTO.diaryId());
            validateDraftDiaryAccess(userId, draftDiary);
            deleteTodayDraftDiaries(userId, draftDiary.getDate());
            return diaryRepository.save(Diary.publish(userId, requestDTO)).getId();
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
        requestDTO.diaryIdList().forEach(draftDiaryId ->
                findDraftDiaryById(draftDiaryId).updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE)
        );
    }

    @Override
    public DraftDiaryResDetailDTO getDraftDiary(Long diaryId, Long userId) {
        final var draftDiary = findDraftDiaryById(diaryId);
        validateDraftDiaryAccess(userId, draftDiary);
        return draftDiaryRepository.getDraftDiaryById(diaryId);
    }

    @Override
    public void deleteTodayDraftDiaries(Long userId, LocalDate draftDiaryDate) {
        draftDiaryRepository.findAllByDateAndUserId(draftDiaryDate, userId)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }

    private DraftDiary findDraftDiaryById(Long diaryId) {
        return draftDiaryRepository.findByIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DraftDiaryNotFoundException(DRAFT_DIARY_NO_ACCESS));
    }

    private void validateDraftDiaryAccess(Long userId, DraftDiary draftDiary) {
        if (!draftDiary.getUserId().equals(userId)) {
            throw new DraftDiaryNoAccessException(ErrorCode.DRAFT_DIARY_NO_ACCESS);
        }
    }
}
