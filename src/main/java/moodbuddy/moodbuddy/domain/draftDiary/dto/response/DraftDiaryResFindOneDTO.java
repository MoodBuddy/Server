package moodbuddy.moodbuddy.domain.draftDiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record DraftDiaryResFindOneDTO(
        @Schema(description = "일기 고유 식별자(diaryId)", example = "1")
        Long diaryId,
        @Schema(description = "일기 날짜", example = "2023-07-02T15:30:00")
        LocalDate diaryDate
) {}
