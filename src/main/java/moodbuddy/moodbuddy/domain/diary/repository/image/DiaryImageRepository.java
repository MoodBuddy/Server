package moodbuddy.moodbuddy.domain.diary.repository.image;

import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    List<DiaryImage> findAllByDiaryIdAndMoodBuddyStatus(Long diaryId, MoodBuddyStatus status);
    Optional<DiaryImage> findByDiaryImgURL(String diaryImgURL);
}
