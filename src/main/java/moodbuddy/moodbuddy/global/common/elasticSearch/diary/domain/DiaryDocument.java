package moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain;

import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryStatus;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.time.LocalDate;

@Document(indexName = "diary")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String diaryTitle;

    @Field(type = FieldType.Date)
    private LocalDate diaryDate;

    @Field(type = FieldType.Text)
    private String diaryContent;

    @Field(type = FieldType.Keyword)
    private String diaryWeather;

    @Field(type = FieldType.Keyword)
    private String diaryEmotion;

    @Field(type = FieldType.Keyword)
    private String diaryStatus;

    @Field(type = FieldType.Keyword)
    private String diarySubject;

    @Field(type = FieldType.Text)
    private String diarySummary;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Boolean)
    private Boolean diaryBookMarkCheck;

    @Field(type = FieldType.Keyword)
    private String diaryFont;

    @Field(type = FieldType.Keyword)
    private String diaryFontSize;

    @Field(type = FieldType.Keyword)
    private MoodBuddyStatus moodBuddyStatus;

    public static DiaryDocument from(Diary diary) {
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
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) {
        this.moodBuddyStatus = moodBuddyStatus;
    }
}
