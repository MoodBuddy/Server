package moodbuddy.moodbuddy.domain.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryImageResConnect {
    Long diaryId;
    String diaryURL;
    double diaryWidth;
    double diaryHeight;
}
