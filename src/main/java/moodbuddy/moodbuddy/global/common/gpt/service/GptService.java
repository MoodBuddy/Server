package moodbuddy.moodbuddy.global.common.gpt.service;

import moodbuddy.moodbuddy.global.common.gpt.dto.GPTResponseDTO;
import reactor.core.publisher.Mono;

public interface GptService {
    Mono<String> classifyDiaryContent(String content);

    Mono<String> summarize(String content);

    Mono<GPTResponseDTO> letterAnswerSave(String worryContent, Integer format);

    Mono<String> emotionComment(String emotion);
}
