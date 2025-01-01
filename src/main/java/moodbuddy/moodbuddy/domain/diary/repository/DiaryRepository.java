package moodbuddy.moodbuddy.domain.diary.repository;

import jakarta.persistence.LockModeType;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {
    Optional<Diary> findByDateAndUserIdAndStatus(LocalDate date, Long userId, DiaryStatus status);
    List<Diary> findAllByDateAndUserIdAndStatus(LocalDate date, Long userId, DiaryStatus status);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Diary> findByIdAndStatusAndMoodBuddyStatus(Long id, DiaryStatus status, MoodBuddyStatus moodBuddyStatus);


    @Query(value = "SELECT * FROM diary WHERE user_id = :userId AND DATE_FORMAT(date, '%Y-%m') = :month AND status = :status", nativeQuery = true)
    List<Diary> findByUserIdAndMonthAndDiaryStatus(@Param("userId") Long userId, @Param("month") String month, @Param("status") String status); // nativeQuery 이므로 status를 enum 값으로 넘겨주면 안된다.

    @Query(value = "SELECT * FROM diary WHERE user_id = :userId AND DATE_FORMAT(date, '%Y-%m-%d') = :day AND status = :status", nativeQuery = true)
    Optional<Diary> findByUserIdAndDayAndDiaryStatus(@Param("userId") Long userId, @Param("day") String day, @Param("status") String status); // nativeQuery 이므로 status를 enum 값으로 넘겨주면 안된다.

    //사용자가 제일 최근에 쓴 일기 요약본 출력
    @Query(value = "SELECT * FROM diary WHERE user_id = :userId ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Optional<Diary> findDiarySummaryById(@Param("userId") Long userId);

    @Query("SELECT d FROM Diary d WHERE d.userId = :userId AND YEAR(d.date) = :year AND MONTH(d.date) = :month")
    List<Diary> findDiaryEmotionByUserIdAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT d FROM Diary d WHERE d.userId = :userId AND YEAR(d.date) = :year AND d.status = :status")
    List<Diary> findAllByYearAndDiaryStatus(@Param("userId") Long userId, @Param("year") int year, @Param("status") DiaryStatus status);

    @Query("SELECT d FROM Diary d WHERE d.userId = :userId AND DATE_FORMAT(d.date, '%Y-%m') = :month AND d.emotion is not null")
    List<Diary> findDiaryEmotionAllByUserIdAndMonth(@Param("userId") Long userId, @Param("month") LocalDate month);
}