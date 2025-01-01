package moodbuddy.moodbuddy.domain.diary.repository.draft;

import jakarta.persistence.LockModeType;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DraftDiaryRepository extends JpaRepository<Diary, Long>, DraftDiaryRepositoryCustom {
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Diary> findByIdAndStatusAndMoodBuddyStatus(Long diaryId, DiaryStatus diaryStatus, MoodBuddyStatus moodBuddyStatus);
    List<Diary> findAllByDateAndUserIdAndStatus(LocalDate diaryDate, Long userId, DiaryStatus diaryStatus);
}
