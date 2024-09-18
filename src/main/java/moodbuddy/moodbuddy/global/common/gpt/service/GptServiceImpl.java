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

    public GptServiceImpl(@Qualifier("gptWebClient") WebClient gptWebClient) {
        this.gptWebClient = gptWebClient;
    }

    @Override
    public Mono<String> classifyDiaryContent(String content) {
        String prompt = "DiaryContent 내용을 보고 \"일상\", \"성장\", \"감정\", \"여행\" 중 어떤 주제에 해당하는 지 주제 값만 \"DAILY\", \"GROWTH\", \"EMOTION\", \"TRAVEL\" 로 출력해줘.\nContent: " + content + "\nCategory:";

        GPTRequestDTO gptRequestDTO = new GPTRequestDTO(model, prompt);

        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent().trim().toUpperCase());
    }

    @Override
    public Mono<String> summarize(String content){
        if(content.length() < 20){
            return Mono.just("요약하기 어려운 일기 내용입니다.");
        }

        String prompt = content + " 이 일기를 서술형인 한 문장으로 요약해주고, 꼭 한 문장이어야 되고, 무조건 요약한 내용만 주세요";
        GPTRequestDTO gptRequestDTO = new GPTRequestDTO(model, prompt);

        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent().trim());
    }

    @Override
    public Mono<String> emotionComment(String emotion){
        String prompt = emotion + " 이 감정에 따른 한 줄 코멘트를 남겨주세요. 글자 수를 20자로 제한해서 꼭 한 줄로 해주세요!";

        GPTRequestDTO gptRequestDTO = new GPTRequestDTO(model, prompt);

        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent().trim());
    }

    @Override
    public Mono<GPTResponseDTO> letterAnswerSave(String worryContent, Integer format){
        String prompt = worryContent + (format == 1 ? " 이 내용에 대해 편한 친구같은 느낌으로 따뜻한 위로의 답변을 해주세요. 이 때 답변을 너무 건방지지 않고 부드럽게, 친구같이 편한 반말로 해주세요"
                : " 이 내용에 대해 편한 친구 같은 느낌으로 따끔한 해결의 답변을 해주세요. 이 때 답변을 너무 건방지지 않고 부드럽게, 친구같이 편한 반말로 해주세요");

        GPTRequestDTO gptRequestDTO = new GPTRequestDTO(model, prompt);

        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)));
    }
}