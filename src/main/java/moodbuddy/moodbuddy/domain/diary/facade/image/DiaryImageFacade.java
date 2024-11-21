package moodbuddy.moodbuddy.domain.diary.facade.image;

import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;

import java.io.IOException;

public interface DiaryImageFacade {
    DiaryImageResURLDTO uploadAndSaveDiaryImage(CloudReqDTO cloudReqDTO) throws IOException;
}
