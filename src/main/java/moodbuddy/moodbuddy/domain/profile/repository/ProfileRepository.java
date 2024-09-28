package moodbuddy.moodbuddy.domain.profile.repository;

import moodbuddy.moodbuddy.domain.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select p from Profile p where p.userId = :userId")
    Optional<Profile> findByUserId(@Param("userId") Long userId);
}
