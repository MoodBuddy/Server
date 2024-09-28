package moodbuddy.moodbuddy.domain.monthcomment.repository;

import moodbuddy.moodbuddy.domain.monthcomment.domain.MonthComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MonthCommentRepository extends JpaRepository<MonthComment,Long> {

    @Query("select mc from MonthComment mc where mc.userId = :userId and mc.commentDate = :formattedMonth")
    Optional<MonthComment> findCommentByUserIdAndMonth(@Param("userId") Long userId, @Param("formattedMonth") String formattedMonth);

    @Modifying
    @Transactional
    @Query("update MonthComment mc set mc.commentContent = :monthComment where mc.userId = :userId and mc.commentDate = :chooseMonth")
    void updateCommentByUserIdAndMonth(@Param("userId") Long userId, @Param("chooseMonth") String chooseMonth, @Param("monthComment") String monthComment);
}
