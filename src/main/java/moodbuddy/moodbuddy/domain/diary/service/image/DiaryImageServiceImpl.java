package moodbuddy.moodbuddy.domain.diary.service.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.repository.image.DiaryImageRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain.DiaryImageDocument;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.repository.DiaryImageDocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                        .diaryId(diary.getDiaryId())
                        .diaryImgURL(diaryImage.getDiaryImgURL())
                        .build())
                .collect(Collectors.toList());

        // Elasticsearch에 저장
        diaryImageDocumentRepository.saveAll(diaryImageDocuments);
    }

    @Override
    @Transactional
    public void deleteAllDiaryImages(Diary diary) {
        List<DiaryImage> diaryImages = diaryImageRepository.findByDiary(diary).orElseGet(Collections::emptyList);
        for (DiaryImage diaryImage : diaryImages) {
            diaryImage.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        }
    }

    @Override
    public List<DiaryImage> findImagesByDiary(Diary diary) {
        return diaryImageRepository.findByDiary(diary).orElseGet(Collections::emptyList);
    }

    @Override
    public String saveProfileImages(MultipartFile newProfileImg) throws IOException {
        return uploadImage(newProfileImg);
    }
}