package moodbuddy.moodbuddy.domain.quddyTI.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.service.QuddyTIService;
import moodbuddy.moodbuddy.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/quddyTI")
@Tag(name = "QuddyTI", description = "쿼디티아이 관련 API")
@RequiredArgsConstructor
@Slf4j
public class QuddyTIApiController {
    private final QuddyTIService quddyTIService;

    /** 구현 완료 **/
    @GetMapping("/findAll")
    @Operation(summary = "쿼디티아이 조회", description = "쿼디티아이 관련 내용을 모두 조회합니다.")
    public ResponseEntity<?> findAll() {
        log.info("[QuddyTIApiController] findAll");
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] QuddyTIApiController findAll", quddyTIService.findAll()));
    }
}
