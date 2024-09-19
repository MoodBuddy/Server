package moodbuddy.moodbuddy.domain.diary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDTO;
import moodbuddy.moodbuddy.domain.diary.entity.Diary;
import moodbuddy.moodbuddy.domain.diary.entity.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.domain.gpt.service.GptService;
import moodbuddy.moodbuddy.global.common.exception.database.DatabaseNullOrEmptyException;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryEmotionServiceImpl implements DiaryEmotionService {
    private final DiaryRepository diaryRepository;
    private final GptService gptService;
    @Override
    @Transactional
    public DiaryResDTO description() throws JsonProcessingException {
        Diary diary = diaryRepository.findDiarySummaryById(JwtUtil.getUserId())
                .orElseThrow(() -> new DatabaseNullOrEmptyException("Diary Summary data not found for kakaoId: " + JwtUtil.getUserId()));

        String emotion = gptService.descriptionContent(diary.getDiaryContent()).block();
        String comment = gptService.emotionComment(emotion).block();

        try {
            DiaryEmotion diaryEmotion = DiaryEmotion.valueOf(emotion);
            diary.setDiaryEmotion(diaryEmotion);
            diaryRepository.save(diary);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid emotion value: " + emotion);
        }

        return DiaryResDTO.builder()
                .emotion(emotion)
                .diaryDate(diary.getDiaryDate())
                .comment(comment)
                .build();
    }
}
