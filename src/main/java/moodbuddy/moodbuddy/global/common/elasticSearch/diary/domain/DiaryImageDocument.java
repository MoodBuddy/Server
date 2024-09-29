package moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "diary_image")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImageDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long diaryId; // 연결된 Diary ID

    @Field(type = FieldType.Text)
    private String diaryImgURL;

    @Field(type = FieldType.Date)
    private LocalDateTime createdTime; // BaseEntity에서 가져온 필드

    @Field(type = FieldType.Date)
    private LocalDateTime updatedTime; // BaseEntity에서 가져온 필드
}

