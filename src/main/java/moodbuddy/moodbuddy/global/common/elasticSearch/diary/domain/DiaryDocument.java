package moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain;

import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String diaryWeather; // DiaryWeather enum

    @Field(type = FieldType.Keyword)
    private String diaryEmotion; // DiaryEmotion enum

    @Field(type = FieldType.Keyword)
    private String diaryStatus; // DiaryStatus enum

    @Field(type = FieldType.Text)
    private String diarySubject; // DiarySubject enum

    @Field(type = FieldType.Text)
    private String diarySummary;

    @Field(type = FieldType.Long)
    private Long userId;


    @Field(type = FieldType.Boolean)
    private Boolean diaryBookMarkCheck;

    @Field(type = FieldType.Keyword)
    private String diaryFont; // DiaryFont enum

    @Field(type = FieldType.Integer)
    private Integer diaryFontSize; // DiaryFontSize enum

    @Field(type = FieldType.Date)
    private LocalDateTime createdTime; // BaseEntity에서 가져온 필드

    @Field(type = FieldType.Date)
    private LocalDateTime updatedTime; // BaseEntity에서 가져온 필드
}
