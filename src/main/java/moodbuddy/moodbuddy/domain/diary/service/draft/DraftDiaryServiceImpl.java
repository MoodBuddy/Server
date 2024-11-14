package moodbuddy.moodbuddy.domain.diary.service.draft;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqDraftSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.repository.draft.DraftDiaryRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.NOT_FOUND_DIARY;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.NOT_FOUND_DRAFT_DIARY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftDiaryServiceImpl implements DraftDiaryService {
    private final DraftDiaryRepository draftDiaryRepository;

    @Override
    @Transactional
    public Diary draftSave(DiaryReqSaveDTO requestDTO, final Long userId) {
        return draftDiaryRepository.save((Diary.ofDraft(
                requestDTO,
                userId)));
    }

    @Override
    public DiaryResDraftFindAllDTO draftFindAll(final Long userId) {
        return draftDiaryRepository.draftFindAllByUserId(userId);
    }

    @Override
    @Transactional
    public void draftSelectDelete(DiaryReqDraftSelectDeleteDTO requestDTO, Long userId) {
        requestDTO.diaryIdList().forEach(diaryId ->
                getDraftDiaryById(diaryId).updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE)
        );
    }

    private Diary getDraftDiaryById(Long diaryId) {
        return draftDiaryRepository.findByDiaryIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(NOT_FOUND_DRAFT_DIARY));
    }
}
