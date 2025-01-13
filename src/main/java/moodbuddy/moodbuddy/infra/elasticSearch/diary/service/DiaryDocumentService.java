package moodbuddy.moodbuddy.infra.elasticSearch.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.infra.elasticSearch.diary.domain.DiaryDocument;

public interface DiaryDocumentService {
    DiaryDocument save(Diary diary);
    void delete(Long diaryId);
}
