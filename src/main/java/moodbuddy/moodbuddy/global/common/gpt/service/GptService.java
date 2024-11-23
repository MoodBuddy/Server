package moodbuddy.moodbuddy.global.common.gpt.service;

import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResEmotionDTO;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTResponseDTO;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface GptService {
    Map<String, String> analyzeDiaryContent(String diaryContent);

    DiaryResEmotionDTO analyzeEmotion();

    GPTResponseDTO letterAnswerSave(String worryContent, Integer format);
}
