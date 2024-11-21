package moodbuddy.moodbuddy.global.common.cloud.service;

import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudUploadDTO;
import java.io.IOException;

public interface CloudService {
    CloudUploadDTO resizeAndUploadImage(CloudReqDTO cloudReqDTO, Long userId) throws IOException;
}
