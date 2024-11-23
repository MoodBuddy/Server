package moodbuddy.moodbuddy.global.common.gpt.service;

import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResEmotionDTO;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTResponseDTO;

import java.util.Map;

public interface GptService {
    Map<String, String> analyzeDiaryContent(String diaryContent);

    DiaryResEmotionDTO analyzeEmotion();

    GPTResponseDTO letterAnswerSave(String worryContent, Integer format);
}
