package moodbuddy.moodbuddy.domain.diary.controller.emotion;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResAnalyzeDTO;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/member/diary")
@Tag(name = "Diary", description = "일기 감정 관련 API")
@RequiredArgsConstructor
public class DiaryEmotionApiController {
    private final GptService gptService;
    @PostMapping("/emotion")
    @Operation(description = "일기 감정 분석")
    public ResponseEntity<DiaryResAnalyzeDTO> emotion() throws JsonProcessingException {
        return ResponseEntity.ok(gptService.analyzeEmotion());
    }
}
