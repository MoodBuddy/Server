package moodbuddy.moodbuddy.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryFont;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryStatus;

import java.time.LocalDate;

public record DiaryResDraftFindOneDTO(
        @Schema(description = "일기 고유 식별자(diaryId)", example = "1")
        Long diaryId,
        @Schema(description = "사용자 고유 식별자(userId)", example = "2")
        Long userId,
        @Schema(description = "일기 날짜", example = "2023-07-02T15:30:00")
        LocalDate diaryDate,
        @Schema(description = "일기 상태(DRAFT, PUBLISHED)", example = "DRAFT")
        DiaryStatus diaryStatus,
        @Schema(description = "일기 폰트", example = "INTER")
        DiaryFont diaryFont,
        @Schema(description = "일기 폰트 사이즈", example = "PX30")
        DiaryFontSize diaryFontSize
) {}
