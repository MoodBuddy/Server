package moodbuddy.moodbuddy.domain.diary.repository.query;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryQueryRepository extends JpaRepository<Diary, Long>, DiaryQueryRepositoryCustom {
}
