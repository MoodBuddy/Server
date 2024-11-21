package moodbuddy.moodbuddy.domain.diary.controller.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import moodbuddy.moodbuddy.domain.diary.facade.image.DiaryImageFacade;
import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v2/member/diaryImage")
@Tag(name = "DiaryImage", description = "일기 이미지 관련 API")
@RequiredArgsConstructor
public class DiaryImageApiController {
    private final DiaryImageFacade diaryImageFacade;

    /** 구현 완료 **/
    @PostMapping("/upload")
    @Operation(summary = "일기 이미지 업로드", description = "일기 이미지를 업로드합니다.")
    public ResponseEntity<CompletableFuture<DiaryImageResURLDTO>> upload(@ModelAttribute CloudReqDTO cloudReqDTO) throws IOException {
        return ResponseEntity.ok().body(diaryImageFacade.uploadAndSaveDiaryImage(cloudReqDTO));
    }
}
