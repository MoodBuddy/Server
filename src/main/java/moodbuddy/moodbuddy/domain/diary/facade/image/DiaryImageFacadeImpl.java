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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryImageFacadeImpl implements DiaryImageFacade {
    private final DiaryImageService diaryImageService;
    private final CloudService cloudService;
    private DiaryImageMapper diaryImageMapper;

    @Override
    @Transactional
    public DiaryImageResURLDTO uploadAndSaveDiaryImage(CloudReqDTO cloudReqDTO) throws IOException {
        final Long userId = JwtUtil.getUserId();
        CloudUploadDTO cloudUploadDTO = cloudService.resizeAndUploadImage(cloudReqDTO, userId);
        DiaryImage diaryImage = diaryImageService.saveImage(cloudUploadDTO);

        return diaryImageMapper.toResURLDTO(diaryImage.getDiaryImgURL());
    }
}
