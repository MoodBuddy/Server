package moodbuddy.moodbuddy.domain.diary.repository.count;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryCountRepository extends JpaRepository<Diary, Long>, DiaryCountRepositoryCustom {
}
