package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.bookMark.entity.BookMark;
import moodbuddy.moodbuddy.domain.diary.entity.Diary;
import moodbuddy.moodbuddy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {
    Optional<BookMark> findByUserIdAndDiary(Long userId, Diary diary);
    void deleteByDiaryId(Long diaryId);
    Optional<BookMark> findByDiaryId(Long diaryId);
}