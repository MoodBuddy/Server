package moodbuddy.moodbuddy.global.common.cloud.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudResUrlDTO;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService{
    private final AmazonS3 amazonS3;
    private final String dot = ".";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.diary_images_folder}")
    private String profileImagesFolder;

    @Override
    public CloudResUrlDTO generatePreSignedUrl() {
        String fileName = UUID.randomUUID() + dot;
        String uploadPath = String.format("%s/%s/%s", profileImagesFolder, JwtUtil.getUserId(), fileName);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, uploadPath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpirationTime());

        return new CloudResUrlDTO(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());
    }

    private Date getExpirationTime() {
        long expirationMillis = System.currentTimeMillis() + (1000 * 60 * 10);
        return new Date(expirationMillis);
    }
}