package moodbuddy.moodbuddy.domain.diary.repository.find;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryFindRepository extends JpaRepository<Diary, Long>, DiaryFindRepositoryCustom {
}
