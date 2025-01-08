package moodbuddy.moodbuddy.domain.diary.service.image;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.repository.image.DiaryImageRepository;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryImageServiceImpl implements DiaryImageService {
    private final DiaryImageRepository diaryImageRepository;

    @Override
    @Transactional
    public void saveAll(final Long diaryId, List<String> imageURLs) {
        for (String imageURL : imageURLs) {
            diaryImageRepository.save(DiaryImage.of(diaryId, imageURL));
        }
    }

    @Override
    @Transactional
    public void deleteAll(final Long diaryId) {
        List<DiaryImage> diaryImages = diaryImageRepository.findAllByDiaryIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE);
        for (DiaryImage diaryImage : diaryImages) {
            diaryImage.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        }
    }
}