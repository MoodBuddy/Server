package moodbuddy.moodbuddy.domain.diary.service.image;

import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiaryImageService {
    DiaryImage save(CloudUploadDTO cloudUploadDTO);
    List<DiaryImage> saveAll(List<String> imageURLs, Long diaryId);
    void deleteAll(final Long diaryId);
    String saveProfileImages(MultipartFile newProfileImg) throws IOException;
}

