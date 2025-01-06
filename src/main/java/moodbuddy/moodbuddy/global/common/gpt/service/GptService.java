package moodbuddy.moodbuddy.global.common.gpt.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResAnalyzeDTO;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTResponseDTO;

public interface GptService {
    DiaryResAnalyzeDTO analyzeDiary(Diary diary);
//    DiaryResAnalyzeDTO analyzeEmotion();
    GPTResponseDTO letterAnswerSave(String worryContent, Integer format);
}
