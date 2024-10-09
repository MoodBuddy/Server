package moodbuddy.moodbuddy.global.common.cloud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudResUploadDTO {
    private String diaryImgFileName;
    private double diaryImgWidth;
    private double diaryImgHeight;
    long diaryImgSize;
    private String diaryImgThumbFileName;
    private double diaryImgThumbWidth;
    private double diaryImgThumbHeight;
    long diaryImgThumbSize;
}
