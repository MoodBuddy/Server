package moodbuddy.moodbuddy.domain.draftDiary.service.image;

import java.util.List;

public interface DraftDiaryImageService {
    void saveAll(final Long diaryId, List<String> imageURLs);
    void deleteAll(final Long diaryId);
}

