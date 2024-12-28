package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {
    Optional<BookMark> findByUserIdAndDiaryId(Long userId, Long diaryId);
    void deleteByDiaryId(Long diaryId);
    Optional<BookMark> findByDiaryId(Long diaryId);
}