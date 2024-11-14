package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {
    Optional<BookMark> findByUserIdAndDiaryId(Long userId, Long diaryId);
    void deleteByDiaryId(Long diaryId);
    Optional<BookMark> findByDiaryId(Long diaryId);

//    @Query(value = "SELECT new moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO(" +
//            "d.id, d.user_id, d.diary_title, d.diary_date, d.diary_content, d.diary_weather, d.diary_emotion, " +
//            "d.diary_status, d.diary_summary, d.diary_subject, d.diary_book_mark_check, di.img_urls, d.diary_font, d.diary_font_size) " +
//            "FROM book_mark bm " +
//            "JOIN diary d ON bm.diary_id = d.id " +
//            "LEFT JOIN (SELECT diary_id, GROUP_CONCAT(diary_img_url SEPARATOR ',') AS img_urls FROM diary_image GROUP BY diary_id) di " +
//            "ON d.id = di.diary_id " +
//            "WHERE bm.user_id = :userId " +
//            "AND d.deleted = false " +
//            "ORDER BY d.diary_date DESC",
//            nativeQuery = true)
//    Page<DiaryResDetailDTO> bookMarkFindAllWithPageable(@Param("userId") Long userId, Pageable pageable);
}