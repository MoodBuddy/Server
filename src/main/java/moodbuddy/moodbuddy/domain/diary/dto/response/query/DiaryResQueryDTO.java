package moodbuddy.moodbuddy.domain.diary.dto.response.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

public record DiaryResQueryDTO(
        Long diaryId,
        String diaryTitle,
        LocalDate diaryDate,
        String diaryContent,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String thumbnail
) {}