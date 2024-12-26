package moodbuddy.moodbuddy.domain.diary.service.image;

import java.util.List;

public interface DiaryImageService {
    void saveAll(List<String> imageURLs, Long diaryId);
    void deleteAll(final Long diaryId);
}

