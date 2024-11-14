package moodbuddy.moodbuddy.domain.diary.repository.draft;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DraftDiaryRepository extends JpaRepository<Diary, Long>, DraftDiaryRepositoryCustom {
    Optional<Diary> findByDiaryIdAndMoodBuddyStatus(Long diaryId, MoodBuddyStatus moodBuddyStatus);
}
