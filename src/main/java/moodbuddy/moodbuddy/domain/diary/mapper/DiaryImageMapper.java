package moodbuddy.moodbuddy.domain.diary.mapper;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryImage;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryImageStatus;

public class DiaryImageMapper {
    public static DiaryImage toDiaryImageEntity(Diary diary) {
        return DiaryImage.builder()
                .diary(diary)
                .diaryImageStatus(DiaryImageStatus.ACTIVE)
                .build();
    }
}
