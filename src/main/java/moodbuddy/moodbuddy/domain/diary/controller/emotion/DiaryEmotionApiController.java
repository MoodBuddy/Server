package moodbuddy.moodbuddy.domain.diary.controller.emotion;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.dto.response.emotion.DiaryResEmotionDTO;
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
    //클라이언트가 일기 작성 -> 일기 요약본 flask서버로 전달 -> flask 서버에서는 모델을 통한 감정 분석 후 결과를 리턴
    @PostMapping("/description")
    @Operation(description = "일기 감정 분석")
    public ResponseEntity<DiaryResEmotionDTO> description() throws JsonProcessingException {
        return ResponseEntity.ok(gptService.analyzeEmotion());
    }
}
