package moodbuddy.moodbuddy.global.common.cloud.service;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.global.common.cloud.ThumbnailGenerator;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudResUploadDTO;
import moodbuddy.moodbuddy.global.common.cloud.mapper.CloudMapper;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CloudServiceImpl implements CloudService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.imgFolder}")
    private String imgFolder;

    private final AmazonS3Client amazonS3Client;
    private final ThumbnailGenerator thumbnailGenerator;


    public CloudResUploadDTO uploadImage(File file, String path, String fileName, String extension) throws IOException {
        path = path.startsWith("/") ? path.replaceFirst("/", "") : path;
        String originFileName = path + "/" + fileName + "." + extension;
        amazonS3Client.putObject(bucket, originFileName, file);
        ImageInfo imageInfo = Imaging.getImageInfo(file);
        File resizeImage = File.createTempFile(fileName + "_thumb", "." + extension);

        try {
            String thumbFileName = path + "/" + fileName + "_thumb" + ".webp";
            thumbnailGenerator.resizeImage(file, resizeImage, resizeImage.getName(), extension);
            ImageInfo resizeImageInfo = Imaging.getImageInfo(resizeImage);
            amazonS3Client.putObject(bucket, thumbFileName, resizeImage);
            return CloudMapper.toCloudResUploadDTO(
                    originFileName, imageInfo.getWidth(), imageInfo.getHeight(), file.length(),
                    thumbFileName, resizeImageInfo.getWidth(), resizeImageInfo.getHeight(), resizeImage.length()
            );
        } finally {
            resizeImage.delete();
        }
    }
}
