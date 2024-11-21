package moodbuddy.moodbuddy.global.common.cloud.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record CloudReqDTO(
        Long userId,
        MultipartFile file,
        String fileExtension
) { }