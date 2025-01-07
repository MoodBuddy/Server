package moodbuddy.moodbuddy.domain.draftDiary.repository.image;

import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import moodbuddy.moodbuddy.domain.draftDiary.domain.image.DraftDiaryImage;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DraftDiaryImageRepository extends JpaRepository<DraftDiaryImage, Long> {
    List<DraftDiaryImage> findAllByDraftDiaryIdAndMoodBuddyStatus(Long draftDiaryId, MoodBuddyStatus status);
}
