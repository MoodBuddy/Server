package moodbuddy.moodbuddy.domain.quddyTI.repository;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuddyTIRepository extends JpaRepository<QuddyTI, Long> {
    Optional<QuddyTI> findByUserIdAndYearAndMonth(
            Long userId,
            String year,
            String month
    );
}
