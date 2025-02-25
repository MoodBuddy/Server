package moodbuddy.moodbuddy.domain.diary.facade.analyze;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResAnalyzeDTO;
import moodbuddy.moodbuddy.domain.diary.service.DiaryService;
import moodbuddy.moodbuddy.external.gpt.service.GptService;
import moodbuddy.moodbuddy.global.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DiaryAnalyzeFacadeImpl implements DiaryAnalyzeFacade {
    private final DiaryService diaryService;
    private final GptService gptService;

    @Override
    @Transactional
    public DiaryResAnalyzeDTO analyze(Long diaryId) {
        final var userId = JwtUtil.getUserId();
        Diary findDiary = diaryService.findDiaryById(diaryId);
        findDiary.validateAccess(userId);
        return gptService.analyzeDiary(diaryService.findDiaryById(diaryId));
    }
}
