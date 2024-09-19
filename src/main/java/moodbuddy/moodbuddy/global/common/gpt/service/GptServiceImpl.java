package moodbuddy.moodbuddy.global.common.gpt.service;

import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTRequestDTO;
import moodbuddy.moodbuddy.global.common.gpt.dto.GPTResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class GptServiceImpl implements GptService{
    private final WebClient gptWebClient;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    private static final String CLASSIFY_PROMPT = "DiaryContent 내용을 보고 \"일상\", \"성장\", \"감정\", \"여행\" 중 어떤 주제에 해당하는 지 " +
            "주제 값만 \"DAILY\", \"GROWTH\", \"EMOTION\", \"TRAVEL\" 로 출력해줘.\nContent: ";
    private static final String SUMMARIZE_PROMPT_SUFFIX = " 이 일기를 서술형인 한 문장으로 요약해주고, 꼭 한 문장이어야 되고, 무조건 요약한 내용만 줘";
    private static final int MIN_CONTENT_LENGTH = 20;
    private static final String DESCRIPTION_PROMPT_SUFFIX = " 이 일기를 보고, 일기에서 느껴지는 감정을 HAPPINESS, ANGER, DISGUST, FEAR, NEUTRAL, SADNESS, SURPRISE 중에서 골라줘. 이 때 다른 설명 없이 값만 줘";
    private static final String EMOTION_COMMENT_PROMPT_SUFFIX = " 이 감정에 따른 한 줄 코멘트를 남겨줘. 글자 수를 20자로 제한해서 꼭 한 줄로 해줘야 해!";
    private static final String GENTLE_LETTER_ANSWER_PROMPT = " 이 내용에 대해 편한 친구같은 느낌으로 따뜻한 위로의 답변을 해줘. 이 때 답변을 너무 건방지지 않고 부드럽게, 친구같이 편한 반말로 해줘";
    private static final String STERN_LETTER_ANSWER_PROMPT = " 이 내용에 대해 편한 친구 같은 느낌으로 따끔한 해결의 답변을 해줘. 이 때 답변을 너무 건방지지 않고 부드럽게, 친구같이 편한 반말로 해줘";
    private static final int RETRY_ATTEMPTS = 3;
    private static final int RETRY_DELAY_SECONDS = 5;

    public GptServiceImpl(@Qualifier("gptWebClient") WebClient gptWebClient) {
        this.gptWebClient = gptWebClient;
    }

    @Override
    public Mono<String> classifyDiaryContent(String content) {
        return getResponseGetContentFromMessage(new GPTRequestDTO(model, CLASSIFY_PROMPT + content + "\nCategory:"), true);
    }

    @Override
    public Mono<String> summarize(String content){
        if(content.length() < MIN_CONTENT_LENGTH){
            return Mono.just("요약하기 어려운 일기 내용입니다.");
        }
        return getResponseGetContentFromMessage(new GPTRequestDTO(model, content + SUMMARIZE_PROMPT_SUFFIX), false);
    }


    @Override
    public Mono<String> descriptionContent(String content) {
        return getResponseGetContentFromMessage(new GPTRequestDTO(model, content + DESCRIPTION_PROMPT_SUFFIX), false);
    }


    @Override
    public Mono<String> emotionComment(String emotion){
        return getResponseGetContentFromMessage(new GPTRequestDTO(model, emotion + EMOTION_COMMENT_PROMPT_SUFFIX), false);
    }

    private Mono<String> getResponseGetContentFromMessage(GPTRequestDTO gptRequestDTO, boolean toUpperCase){
        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .map(gptResponseDTO -> {
                    String content = gptResponseDTO.getChoices().get(0).getMessage().getContent().trim();
                    return toUpperCase ? content.toUpperCase() : content;
                });
    }

    @Override
    public Mono<GPTResponseDTO> letterAnswerSave(String worryContent, Integer format){
        return getResponseForLetterAnswerSaveMethod(new GPTRequestDTO(model, worryContent + (format == 1 ? GENTLE_LETTER_ANSWER_PROMPT : STERN_LETTER_ANSWER_PROMPT)));
    }

    private Mono<GPTResponseDTO> getResponseForLetterAnswerSaveMethod(GPTRequestDTO gptRequestDTO){
        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .retryWhen(Retry.backoff(RETRY_ATTEMPTS, Duration.ofSeconds(RETRY_DELAY_SECONDS)));
    }
}