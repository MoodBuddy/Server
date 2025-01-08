package moodbuddy.moodbuddy.domain.diary.service.image;

import java.util.List;

public interface DiaryImageService {
    void saveAll(final Long diaryId, List<String> imageURLs);
    void deleteAll(final Long diaryId);
}

