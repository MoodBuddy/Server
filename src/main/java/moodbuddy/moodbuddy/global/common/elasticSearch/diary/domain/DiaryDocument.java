package moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain;

import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Document(indexName = "diary")
public class DiaryDocument {
    @Id
    @Field(name = "id", type = FieldType.Long)
    private Long id;

    @Field(name = "diary_title", type = FieldType.Text)
    private String diaryTitle;

    @Field(name = "diary_date", type = FieldType.Date)
    private LocalDate diaryDate;

    @Field(name = "diary_content", type = FieldType.Text)
    private String diaryContent;

    @Field(name = "diary_weather", type = FieldType.Keyword)
    private DiaryWeather diaryWeather;

    @Field(name = "diary_emotion", type = FieldType.Keyword)
    private DiaryEmotion diaryEmotion;

    @Field(name = "diary_status", type = FieldType.Keyword)
    private DiaryStatus diaryStatus;

    @Field(name = "diary_subject", type = FieldType.Keyword)
    private DiarySubject diarySubject;

    @Field(name = "diary_summary", type = FieldType.Text)
    private String diarySummary;

    @Field(name = "user_id", type = FieldType.Long)
    private Long userId;

    @Field(name = "diary_book_mark_check", type = FieldType.Boolean)
    private Boolean diaryBookMarkCheck; // 북마크 여부

    /** 추가 칼럼 **/
    @Field(name = "diary_font", type = FieldType.Keyword)
    private DiaryFont diaryFont;

    @Field(name = "diary_font_size", type = FieldType.Keyword)
    private DiaryFontSize diaryFontSize;
}
