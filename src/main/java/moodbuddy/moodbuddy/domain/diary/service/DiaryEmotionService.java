package moodbuddy.moodbuddy.domain.diary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResEmotionDTO;

public interface DiaryEmotionService {
    //일기 감정 분석
    DiaryResEmotionDTO description() throws JsonProcessingException;
}
