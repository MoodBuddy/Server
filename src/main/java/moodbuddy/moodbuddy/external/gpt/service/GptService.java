package moodbuddy.moodbuddy.external.gpt.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResAnalyzeDTO;
import moodbuddy.moodbuddy.external.gpt.dto.GptResponseDTO;

public interface GptService {
    DiaryResAnalyzeDTO analyzeDiary(Diary diary);
//    DiaryResAnalyzeDTO analyzeEmotion();
    GptResponseDTO letterAnswerSave(String worryContent, Integer format);
}
