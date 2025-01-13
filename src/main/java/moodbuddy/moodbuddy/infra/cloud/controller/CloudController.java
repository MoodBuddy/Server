package moodbuddy.moodbuddy.infra.cloud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.infra.cloud.dto.response.CloudResUrlDTO;
import moodbuddy.moodbuddy.infra.cloud.service.CloudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/member/cloud")
@Tag(name = "Cloud", description = "클라우드 관련 API")
@RequiredArgsConstructor
public class CloudController {
    private final CloudService cloudService;

    @PostMapping("/generate-url")
    @Operation(summary = "preSignedUrl 생성 API", description = "preSignedUrl 생성 API 입니다.")
    public ResponseEntity<CloudResUrlDTO> generatePreSignedUrl() {
        return ResponseEntity.ok(cloudService.generatePreSignedUrl());
    }
}
