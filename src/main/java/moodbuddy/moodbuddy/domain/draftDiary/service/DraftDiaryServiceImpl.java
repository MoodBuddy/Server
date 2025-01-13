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
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.domain.draftDiary.exception.DraftDiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.domain.draftDiary.exception.DraftDiaryNotFoundException;
import moodbuddy.moodbuddy.domain.draftDiary.exception.DraftDiaryNoAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import static moodbuddy.moodbuddy.global.error.ErrorCode.DRAFT_DIARY_NO_ACCESS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftDiaryServiceImpl implements DraftDiaryService {
    private final DraftDiaryRepository draftDiaryRepository;
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public Long saveDraftDiary(final Long userId, DraftDiaryReqSaveDTO requestDTO) {
        return draftDiaryRepository.save((DraftDiary.of(
                requestDTO,
                userId))).getId();
    }

    @Override
    @Transactional
    public Long publishDraftDiary(final Long userId, DraftDiaryReqPublishDTO requestDTO) {
        try {
            var findDraftDiary = findDraftDiaryById(requestDTO.diaryId());
            validateDraftDiaryAccess(userId, findDraftDiary);
            deleteDraftDiariesByDate(userId, findDraftDiary.getDate());
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
    public void deleteDraftDiaries(final Long userId, DraftDiaryReqSelectDeleteDTO requestDTO) {
        requestDTO.diaryIdList().forEach(draftDiaryId ->
                findDraftDiaryById(draftDiaryId).updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE)
        );
    }

    @Override
    public DraftDiaryResDetailDTO getDraftDiary(final Long userId, final Long diaryId) {
        final var findDraftDiary = findDraftDiaryById(diaryId);
        validateDraftDiaryAccess(userId, findDraftDiary);
        return draftDiaryRepository.getDraftDiaryById(diaryId);
    }

    @Override
    public void deleteDraftDiariesByDate(final Long userId, LocalDate draftDiaryDate) {
        draftDiaryRepository.findAllByUserIdAndDate(userId, draftDiaryDate)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }

    private DraftDiary findDraftDiaryById(final Long diaryId) {
        return draftDiaryRepository.findByIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DraftDiaryNotFoundException(DRAFT_DIARY_NO_ACCESS));
    }

    private void validateDraftDiaryAccess(final Long userId, DraftDiary draftDiary) {
        if (!draftDiary.getUserId().equals(userId)) {
            throw new DraftDiaryNoAccessException(ErrorCode.DRAFT_DIARY_NO_ACCESS);
        }
    }
}
