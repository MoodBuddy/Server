package moodbuddy.moodbuddy.global.common.cloud.mapper;

import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudResUploadDTO;

public class CloudMapper {
    public static CloudResUploadDTO toCloudResUploadDTO(String originFileName, int width, int height, long size, String thumbFileName, int thumbFileWidth, int thumbFileHeight, long thumbFileSize) {
        return CloudResUploadDTO.builder()
                .diaryImgFileName(originFileName)
                .diaryImgWidth(width)
                .diaryImgHeight(height)
                .diaryImgSize(size)
                .diaryImgThumbFileName(thumbFileName)
                .diaryImgThumbWidth(thumbFileWidth)
                .diaryImgThumbHeight(thumbFileHeight)
                .diaryImgThumbSize(thumbFileSize)
                .build();
    }
}
