package moodbuddy.moodbuddy.domain.diary.service.draft;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.repository.draft.DraftDiaryRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.*;
import moodbuddy.moodbuddy.global.common.exception.diary.draft.DraftDiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.global.common.exception.diary.draft.DraftDiaryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    public Diary updateDraftDiary(DiaryReqUpdateDTO requestDTO, Map<String, String> gptResults, Long userId) {
        try {
            Diary findDiary = findDraftDiaryById(requestDTO.diaryId());
            validateDiaryAccess(findDiary, userId);
            findDiary.updateDiary(requestDTO, gptResults);
            deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
            return findDiary;
        } catch (OptimisticLockException ex) {
            throw new DraftDiaryConcurrentUpdateException(ErrorCode.DRAFT_DIARY_CONCURRENT_UPDATE);
        }
    }

    @Override
    public List<DraftDiaryResFindOneDTO> getDraftDiaries(final Long userId) {
        return draftDiaryRepository.findAllByUserId(userId);
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
        return draftDiaryRepository.findOneByDiaryId(diaryId);
    }

    private void deleteTodayDraftDiaries(LocalDate diaryDate, Long userId) {
        draftDiaryRepository.findAllByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.DRAFT)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }

    private Diary findDraftDiaryById(Long diaryId) {
        return draftDiaryRepository.findByDiaryIdAndDiaryStatusAndMoodBuddyStatus(diaryId, DiaryStatus.DRAFT, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DraftDiaryNotFoundException(DRAFT_DIARY_NOT_FOUND));
    }

    private void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.DIARY_NO_ACCESS);
        }
    }
}
