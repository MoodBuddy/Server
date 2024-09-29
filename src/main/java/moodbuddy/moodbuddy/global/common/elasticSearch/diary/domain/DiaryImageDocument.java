package moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Document(indexName = "diary_image")
public class DiaryImageDocument {

    @Id
    @Field(name = "diary_image_id", type = FieldType.Long)
    private Long id;

    @Field(name = "diary_id", type = FieldType.Long)
    private Long diaryId;

    @Field(name = "diary_img_url", type = FieldType.Text)
    private String diaryImgURL;
}

