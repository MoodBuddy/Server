package moodbuddy.moodbuddy.domain.diary.service.draft;

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
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNoAccessException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.NOT_FOUND_DIARY;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.NOT_FOUND_DRAFT_DIARY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftDiaryServiceImpl implements DraftDiaryService {
    private final DraftDiaryRepository draftDiaryRepository;

    @Override
    @Transactional
    public Diary save(DiaryReqSaveDTO requestDTO, final Long userId) {
        return draftDiaryRepository.save((Diary.ofDraft(
                requestDTO,
                userId)));
    }

    @Override
    public Diary update(DiaryReqUpdateDTO requestDTO, Map<String, String> gptResults, Long userId) {
        Diary findDiary = getDraftDiaryById(requestDTO.diaryId());
        validateDiaryAccess(findDiary, userId);
        findDiary.updateDiary(requestDTO, gptResults);
        deleteTodayDraftDiaries(requestDTO.diaryDate(), userId);
        return findDiary;
    }

    @Override
    public List<DraftDiaryResFindOneDTO> findAll(final Long userId) {
        return draftDiaryRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void selectDelete(DraftDiaryReqSelectDeleteDTO requestDTO, Long userId) {
        requestDTO.diaryIdList().forEach(diaryId ->
                getDraftDiaryById(diaryId).updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE)
        );
    }

    @Override
    public DraftDiaryResDetailDTO findOneByDiaryId(Long diaryId, Long userId) {
        final Diary findDiary = getDraftDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        return draftDiaryRepository.findOneByDiaryId(diaryId);
    }

    private void deleteTodayDraftDiaries(LocalDate diaryDate, Long userId) {
        draftDiaryRepository.findAllByDiaryDateAndUserIdAndDiaryStatus(diaryDate, userId, DiaryStatus.DRAFT)
                .forEach(draftDiary -> draftDiary.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE));
    }

    private Diary getDraftDiaryById(Long diaryId) {
        return draftDiaryRepository.findByDiaryIdAndDiaryStatusAndMoodBuddyStatus(diaryId, DiaryStatus.DRAFT, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(NOT_FOUND_DRAFT_DIARY));
    }

    private void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.NO_ACCESS_DIARY);
        }
    }
}
