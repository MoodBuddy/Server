package moodbuddy.moodbuddy.domain.quddyTI.repository;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuddyTIRepository extends JpaRepository<QuddyTI, Long> {
    Optional<List<QuddyTI>> findByUserId(Long userId);
    Optional<QuddyTI> findByUserIdAndQuddyTIYearMonth(Long userId, String yearMonth);
}
