package moodbuddy.moodbuddy.domain.diary.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryImageRepository;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain.DiaryImageDocument;
import moodbuddy.moodbuddy.global.common.elasticSearch.repository.DiaryImageDocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DiaryImageServiceImpl implements DiaryImageService {
    private final DiaryImageRepository diaryImageRepository;
    private final DiaryImageDocumentRepository diaryImageDocumentRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.imgFolder}")
    private String imgFolder;

    @Override
    @Transactional
    public void saveDiaryImages(List<MultipartFile> diaryImgList, Diary diary) throws IOException {
        if (diaryImgList != null && !diaryImgList.isEmpty()) {
            List<String> diaryUrlList = diaryImgList.stream()
                    .filter(imageFile -> imageFile != null && !imageFile.isEmpty())
                    .map(this::uploadImage)
                    .collect(Collectors.toList());

            saveDiaryImageEntities(diaryUrlList, diary);
        }
    }

    private String uploadImage(MultipartFile diaryImg) {
        try {
            String originalFilename = diaryImg.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            String filePath = imgFolder + "/" + fileName;

            amazonS3.putObject(bucket, filePath, diaryImg.getInputStream(), new ObjectMetadata());
            return amazonS3.getUrl(bucket, filePath).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    private void saveDiaryImageEntities(List<String> diaryUrlList, Diary diary) {
        List<DiaryImage> diaryImages = diaryUrlList.stream()
                .map(url -> DiaryImage.builder()
                        .diaryImgURL(url)
                        .diary(diary)
                        .build())
                .collect(Collectors.toList());

        diaryImageRepository.saveAll(diaryImages);

        // Elasticsearch에 DiaryImageDocument 저장
        saveDiaryImageDocuments(diaryImages, diary);
    }

    private void saveDiaryImageDocuments(List<DiaryImage> diaryImages, Diary diary) {
        List<DiaryImageDocument> diaryImageDocuments = diaryImages.stream()
                .map(diaryImage -> DiaryImageDocument.builder()
                        .id(diaryImage.getId())
                        .diaryId(diary.getId())
                        .diaryImgURL(diaryImage.getDiaryImgURL())
                        .build())
                .collect(Collectors.toList());

        // Elasticsearch에 저장
        diaryImageDocumentRepository.saveAll(diaryImageDocuments);
    }

    @Override
    @Transactional
    public void deleteAllDiaryImages(Diary diary) throws IOException {
        List<DiaryImage> diaryImages = diaryImageRepository.findByDiary(diary).orElseGet(Collections::emptyList);
        for (DiaryImage diaryImage : diaryImages) {
            deleteImageFromS3(diaryImage.getDiaryImgURL());
            diaryImageRepository.delete(diaryImage);
        }
    }

    @Transactional
    public void deleteDiaryImage(DiaryImage diaryImage) throws IOException {
        deleteImageFromS3(diaryImage.getDiaryImgURL());
        diaryImageRepository.delete(diaryImage);
    }

    private void deleteImageFromS3(String imageUrl) throws IOException {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String filePath = imgFolder + "/" + fileName;
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));
    }

    @Override
    public List<DiaryImage> findImagesByDiary(Diary diary) {
        return diaryImageRepository.findByDiary(diary).orElseGet(Collections::emptyList);
    }

    @Override
    public String saveProfileImages(MultipartFile newProfileImg) throws IOException {
        return uploadImage(newProfileImg);
    }

    @Transactional
    public void deleteExcludingImages(Diary diary, List<String> existingImgUrls) throws IOException {
        if (existingImgUrls == null) {
            existingImgUrls = new ArrayList<>();
        }

        List<DiaryImage> diaryImages = findImagesByDiary(diary);
        for (DiaryImage diaryImage : diaryImages) {
            String imageUrl = diaryImage.getDiaryImgURL().trim();
            if (!existingImgUrls.contains(imageUrl)) {
                deleteDiaryImage(diaryImage);
            }
        }
    }
}