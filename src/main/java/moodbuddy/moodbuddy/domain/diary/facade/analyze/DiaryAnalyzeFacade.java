package moodbuddy.moodbuddy.domain.diary.facade.analyze;

import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResAnalyzeDTO;

public interface DiaryAnalyzeFacade {
    DiaryResAnalyzeDTO analyze(Long diaryId);
}
