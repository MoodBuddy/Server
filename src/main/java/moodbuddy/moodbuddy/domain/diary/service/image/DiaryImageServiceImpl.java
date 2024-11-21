package moodbuddy.moodbuddy.domain.diary.service.image;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.repository.image.DiaryImageRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudUploadDTO;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diaryImage.DiaryImageNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryImageServiceImpl implements DiaryImageService {
    private final DiaryImageRepository diaryImageRepository;

    @Override
    @Transactional
    public DiaryImage saveImage(CloudUploadDTO uploadDTO) {
        return diaryImageRepository.save(DiaryImage.from(uploadDTO));
    }

    @Override
    @Transactional
    public List<DiaryImage> saveImages(List<String> imageURLs, Long diaryId) {
        List<DiaryImage> diaryImages = new ArrayList<>();
        for (String imageURL : imageURLs) {
            DiaryImage diaryImage = getDiaryImageByImageURL(imageURL);
            diaryImage.updateStatus(diaryId);
            diaryImages.add(diaryImage);
        }
        return diaryImages;
    }

    @Override
    @Transactional
    public void deleteAllDiaryImages(final Long diaryId) {
        List<DiaryImage> diaryImages = diaryImageRepository.findAllByDiaryIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE);
        for (DiaryImage diaryImage : diaryImages) {
            diaryImage.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        }
    }

    @Override
    public String saveProfileImages(MultipartFile newProfileImg) {
//        return uploadImage(newProfileImg);
        return null;
    }

    private DiaryImage getDiaryImageByImageURL(String imageURL) {
        return diaryImageRepository.findByDiaryImgURL(imageURL)
                .orElseThrow(() -> new DiaryImageNotFoundException(ErrorCode.NOT_FOUND_DIARY_IMAGE));
    }
}