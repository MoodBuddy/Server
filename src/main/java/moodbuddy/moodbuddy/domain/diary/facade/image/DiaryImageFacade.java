package moodbuddy.moodbuddy.domain.diary.facade.image;

import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface DiaryImageFacade {
    CompletableFuture<DiaryImageResURLDTO> uploadAndSaveDiaryImage(CloudReqDTO cloudReqDTO) throws IOException;
}
