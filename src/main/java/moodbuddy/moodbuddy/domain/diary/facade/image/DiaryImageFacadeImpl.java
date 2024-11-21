package moodbuddy.moodbuddy.domain.diary.facade.image;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import moodbuddy.moodbuddy.domain.diary.mapper.image.DiaryImageMapper;
import moodbuddy.moodbuddy.domain.diary.service.image.DiaryImageService;
import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudUploadDTO;
import moodbuddy.moodbuddy.global.common.cloud.service.CloudService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryImageFacadeImpl implements DiaryImageFacade {
    private final DiaryImageService diaryImageService;
    private final CloudService cloudService;
    private final DiaryImageMapper diaryImageMapper;

    @Override
    @Transactional
    @Async
    public CompletableFuture<DiaryImageResURLDTO> uploadAndSaveDiaryImage(CloudReqDTO cloudReqDTO) throws IOException {
        CloudUploadDTO cloudUploadDTO = cloudService.resizeAndUploadImage(cloudReqDTO);
        DiaryImage diaryImage = diaryImageService.saveImage(cloudUploadDTO);

        return CompletableFuture.completedFuture(diaryImageMapper.toResURLDTO(diaryImage.getDiaryImgURL()));
    }
}
