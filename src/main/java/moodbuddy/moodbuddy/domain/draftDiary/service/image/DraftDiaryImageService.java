package moodbuddy.moodbuddy.domain.draftDiary.service.image;

import java.util.List;

public interface DraftDiaryImageService {
    void saveAll(List<String> imageURLs, Long diaryId);
    void deleteAll(final Long diaryId);
}

