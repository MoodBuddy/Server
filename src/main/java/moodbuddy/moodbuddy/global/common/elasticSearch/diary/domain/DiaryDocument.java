package moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain;

import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
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
                .diaryTitle(diary.getTitle())
                .diaryDate(diary.getDate())
                .diaryContent(diary.getContent().toString())
                .diaryWeather(diary.getWeather().toString())
                .diaryEmotion(null)
                .diarySummary(null)
                .diarySubject(null)
                .userId(diary.getUserId())
                .diaryBookMarkCheck(false)
                .diaryFont(diary.getFont().toString())
                .diaryFontSize(diary.getFontSize().toString())
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) {
        this.moodBuddyStatus = moodBuddyStatus;
    }
}
