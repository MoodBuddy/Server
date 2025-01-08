package moodbuddy.moodbuddy.domain.draftDiary.service.image;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.repository.image.DiaryImageRepository;
import moodbuddy.moodbuddy.domain.draftDiary.domain.image.DraftDiaryImage;
import moodbuddy.moodbuddy.domain.draftDiary.repository.image.DraftDiaryImageRepository;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftDiaryImageServiceImpl implements DraftDiaryImageService {
    private final DraftDiaryImageRepository draftDiaryImageRepository;

    @Override
    @Transactional
    public void saveAll(final Long draftDiaryId, List<String> imageURLs) {
        for (String imageURL : imageURLs) {
            draftDiaryImageRepository.save(DraftDiaryImage.of(draftDiaryId, imageURL));
        }
    }

    @Override
    @Transactional
    public void deleteAll(final Long draftDiaryId) {
        List<DraftDiaryImage> draftDiaryImages = draftDiaryImageRepository.findAllByDraftDiaryIdAndMoodBuddyStatus(draftDiaryId, MoodBuddyStatus.ACTIVE);
        for (DraftDiaryImage draftDiaryImage : draftDiaryImages) {
            draftDiaryImage.updateMoodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE);
        }
    }
}