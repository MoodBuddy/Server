package moodbuddy.moodbuddy.domain.diary.repository.image;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.image.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    Optional<List<DiaryImage>> findByDiary(Diary diary);

}
