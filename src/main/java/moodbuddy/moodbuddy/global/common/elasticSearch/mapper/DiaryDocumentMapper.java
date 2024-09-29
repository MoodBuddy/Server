package moodbuddy.moodbuddy.global.common.elasticSearch.mapper;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryStatus;
import moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain.DiaryDocument;
import org.modelmapper.ModelMapper;

public class DiaryDocumentMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static DiaryDocument toDiaryDocument(Diary diary) {
        return DiaryDocument.builder()
                .diaryTitle(diary.getDiaryTitle())
                .diaryDate(diary.getDiaryDate())
                .diaryContent(diary.getDiaryContent().toString())
                .diaryWeather(diary.getDiaryWeather().toString())
                .diaryStatus(DiaryStatus.PUBLISHED.toString())
                .diarySummary(diary.getDiarySummary())
                .diarySubject(diary.getDiarySubject().toString())
                .userId(diary.getUserId())
                .diaryBookMarkCheck(false)
                .diaryFont(diary.getDiaryFont().toString())
                .diaryFontSize(diary.getDiaryFontSize().toString())
                .build();
    }
}
