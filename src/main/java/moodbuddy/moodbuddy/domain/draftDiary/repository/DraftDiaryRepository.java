package moodbuddy.moodbuddy.domain.draftDiary.repository;

import jakarta.persistence.LockModeType;
import moodbuddy.moodbuddy.domain.draftDiary.domain.DraftDiary;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DraftDiaryRepository extends JpaRepository<DraftDiary, Long>, DraftDiaryRepositoryCustom {
    @Lock(LockModeType.OPTIMISTIC)
    Optional<DraftDiary> findByIdAndUserIdAndMoodBuddyStatus(Long userId, Long diaryId, MoodBuddyStatus moodBuddyStatus);
    List<DraftDiary> findAllByUserIdAndDate(Long userId, LocalDate diaryDate);
}
