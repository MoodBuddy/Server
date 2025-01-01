package moodbuddy.moodbuddy.domain.diary.dto.response.find;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

public record DiaryResFindDTO (
        Long diaryId,
        String diaryTitle,
        LocalDate diaryDate,
        String diaryContent,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String thumbnail
)
{}
