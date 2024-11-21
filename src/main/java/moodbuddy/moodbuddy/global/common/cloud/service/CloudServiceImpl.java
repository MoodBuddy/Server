package moodbuddy.moodbuddy.global.common.cloud.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.global.common.cloud.ThumbnailGenerator;
import moodbuddy.moodbuddy.global.common.cloud.dto.request.CloudReqDTO;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudUploadDTO;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService {
    private final AmazonS3 amazonS3;
    private final ThumbnailGenerator thumbnailGenerator;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.diary_images_folder}")
    private String diaryImagesFolder;

    @Override
    @Transactional
    public CloudUploadDTO resizeAndUploadImage(CloudReqDTO requestDTO, Long userId) throws IOException {
        String fileExtension = requestDTO.fileExtension();
        String fileName = UUID.randomUUID() + "." + fileExtension;

        File originalFile = requestDTO.file();
        File resizeFile = File.createTempFile("resize_", fileExtension);
        ImageInfo originalFileInfo = Imaging.getImageInfo(originalFile);

        try {
            thumbnailGenerator.resizeImage(
                    originalFile, resizeFile, fileName, fileExtension
            );

            String uploadPath = String.format("%s/%s/%s", diaryImagesFolder, userId, fileName);
            String fileUrl = uploadToS3(resizeFile, uploadPath);
            ImageInfo resizeFileInfo = Imaging.getImageInfo(resizeFile);

            return CloudUploadDTO.of(fileUrl, fileName, uploadPath, originalFileInfo, resizeFileInfo);
        } finally {
            originalFile.delete();
            resizeFile.delete();
        }
    }

    private String uploadToS3(File file, String uploadPath) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());

        amazonS3.putObject(bucket, uploadPath, file);
        return amazonS3.getUrl(bucket, uploadPath).toString();
    }
}