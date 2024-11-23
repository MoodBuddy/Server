package moodbuddy.moodbuddy.global.common.cloud.dto.response;

import org.apache.commons.imaging.ImageInfo;

public record CloudUploadDTO(
        String fileUrl,
        String fileName,
        String filePath,
        int originalWidth,
        int originalHeight,
        int resizeWidth,
        int resizeHeight
) {
    public static CloudUploadDTO of(String fileUrl, String fileName, String filePath, ImageInfo originalFileInfo, ImageInfo resizeFileInfo) {
        return new CloudUploadDTO(fileUrl, fileName, filePath, originalFileInfo.getWidth(), originalFileInfo.getHeight(), resizeFileInfo.getWidth(), resizeFileInfo.getHeight());
    }
}