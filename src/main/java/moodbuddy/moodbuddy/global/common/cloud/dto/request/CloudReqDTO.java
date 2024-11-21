package moodbuddy.moodbuddy.global.common.cloud.dto.request;

import java.io.File;

public record CloudReqDTO(
        File file,
        String fileExtension
) { }