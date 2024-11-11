package moodbuddy.moodbuddy.global.common.gpt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResEmotionDTO;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import moodbuddy.moodbuddy.global.common.exception.gpt.ParsingContentException;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTRequestDTO;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTResponseDTO;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GptServiceImpl implements GptService{
    private final WebClient gptWebClient;
    private final DiaryRepository diaryRepository;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    // 일기 주제 + 요약 프롬프트
    private static final String CONTENT_ANALYSIS_PROMPT = " 이 일기 내용을 분석하여, 주제에 해당하는 값을 다음 중에서 선택해 줘: \"일상\", \"성장\", \"감정\", \"여행\". 주제 값은 \"DAILY\", \"GROWTH\", \"EMOTION\", \"TRAVEL\" 중 하나로 출력해 줘." +
            " 그리고 이 일기를 서술형인 한 문장으로 요약해 주고, 요약 내용은 반드시 한 문장이어야 하며, 무조건 요약한 내용만 출력해 줘." +
            " 마지막으로, 두 가지 응답을 다른 설명 없이 다음 형식으로 반환해 줘: { \"subject\": \"주제 값\", \"summary\": \"요약 내용\" }.";
    // 일기 감정 + 감정 한 마디 프롬프트
    private static final String EMOTION_ANALYSIS_PROMPT = " 이 일기 내용을 분석하여, 일기에서 느껴지는 감정을 \"HAPPINESS\", \"ANGER\", \"DISGUST\", \"FEAR\", \"NEUTRAL\", \"SADNESS\", \"SURPRISE\" 중에서 골라줘. " +
            " 그리고 이 감정에 따른 한 줄 코멘트를 남겨줘. 글자 수는 20자로 제한하며, 꼭 한 줄로 작성해 줘." +
            " 마지막으로, 두 가지 응답을 다른 설명 없이 다음 형식으로 반환해 줘: { \"emotion\": \"감정 값\", \"comment\": \"한 줄 코멘트\" }.";
    // 따뜻한 위로의 쿼디 답변 프롬프트
    private static final String GENTLE_LETTER_ANSWER_PROMPT = " 이 내용에 대해 편한 친구같은 느낌으로 따뜻한 위로의 답변을 해줘. 이 때 답변을 너무 건방지지 않고 부드럽게, 친구같이 편한 반말로 해줘";
    // 따끔한 해결의 쿼디 답변 프롬프트
    private static final String STERN_LETTER_ANSWER_PROMPT = " 이 내용에 대해 편한 친구 같은 느낌으로 따끔한 해결의 답변을 해줘. 이 때 답변을 너무 건방지지 않고 부드럽게, 친구같이 편한 반말로 해줘";
    private static final int RETRY_ATTEMPTS = 3;
    private static final int RETRY_DELAY_SECONDS = 5;

    public GptServiceImpl(@Qualifier("gptWebClient") WebClient gptWebClient, DiaryRepository diaryRepository) {
        this.gptWebClient = gptWebClient;
        this.diaryRepository = diaryRepository;
    }

    @Override
    public Map<String, String> analyzeDiaryContent(String diaryContent){
        List<String> keys = List.of("subject", "summary");
        return getGPTResponseMap(new GPTRequestDTO(model, diaryContent + CONTENT_ANALYSIS_PROMPT), keys);
    }

    @Override
    public DiaryResEmotionDTO analyzeEmotion(){
        Diary diary = diaryRepository.findDiarySummaryById(JwtUtil.getUserId())
                .orElseThrow(() -> new DiaryNotFoundException(ErrorCode.NOT_FOUND_DIARY));

        List<String> keys = List.of("emotion", "comment");
        Map<String, String> responseMap = getGPTResponseMap(new GPTRequestDTO(model, diary.getDiaryContent() + EMOTION_ANALYSIS_PROMPT), keys);

        diary.updateDiaryEmotion(DiaryEmotion.valueOf(responseMap.get("emotion")));
        diaryRepository.save(diary);

        return DiaryResEmotionDTO.builder()
                .emotion(responseMap.get("emotion"))
                .diaryDate(diary.getDiaryDate())
                .comment(responseMap.get("comment"))
                .build();
    }

    private Map<String, String> getGPTResponseMap(GPTRequestDTO gptRequestDTO, List<String> keys) {
        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .map(gptResponseDTO -> {
                    String content = gptResponseDTO.getChoices().get(0).getMessage().getContent();
                    return parseJsonContent(content, keys);
                })
                .block();
    }

    private Map<String, String> parseJsonContent(String content, List<String> keys) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            for (String key : keys) {
                responseMap.put(key, jsonNode.path(key).asText());
            }
        } catch (Exception e) {
            throw new ParsingContentException(ErrorCode.GPT_PARSE_ERROR, content);
        }
        return responseMap;
    }

    @Override
    public GPTResponseDTO letterAnswerSave(String worryContent, Integer format){
        return getResponseForLetterAnswerSaveMethod(new GPTRequestDTO(model, worryContent + (format == 1 ? GENTLE_LETTER_ANSWER_PROMPT : STERN_LETTER_ANSWER_PROMPT)));
    }

    private GPTResponseDTO getResponseForLetterAnswerSaveMethod(GPTRequestDTO gptRequestDTO){
        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .retryWhen(Retry.backoff(RETRY_ATTEMPTS, Duration.ofSeconds(RETRY_DELAY_SECONDS)))
                .block();
    }
}