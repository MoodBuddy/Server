package moodbuddy.moodbuddy.infra.elasticSearch.diary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.infra.elasticSearch.diary.domain.DiaryDocument;
import moodbuddy.moodbuddy.infra.elasticSearch.diary.repository.DiaryDocumentRepository;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static moodbuddy.moodbuddy.global.error.ErrorCode.DIARY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DiaryDocumentServiceImpl implements DiaryDocumentService {
    private final DiaryDocumentRepository diaryDocumentRepository;

    @Override
    public DiaryDocument save(Diary diary) {
        return diaryDocumentRepository.save(DiaryDocument.from(diary));
    }

    @Override
    public void delete(Long diaryId) {
        findDiaryDocumentById(diaryId).updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
    }

    private DiaryDocument findDiaryDocumentById(Long diaryDocumentId) {
        return diaryDocumentRepository.findById(diaryDocumentId)
                .orElseThrow(() -> new DiaryNotFoundException(DIARY_NOT_FOUND));
    }
}
