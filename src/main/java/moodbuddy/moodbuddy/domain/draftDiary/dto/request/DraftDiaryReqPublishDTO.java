package moodbuddy.moodbuddy.domain.draftDiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record DraftDiaryReqPublishDTO (
        @Schema(description = "수정할 일기 고유 식별자(draftDiaryId)", example = "1")
        @NotNull
        Long diaryId,
        @Schema(description = "수정할 일기 제목", example = "쿼카의 하카")
        @NotNull
        String diaryTitle,
        @Schema(description = "일기 날짜", example = "2023-07-02")
        @NotNull
        LocalDate diaryDate,
        @Schema(description = "수정할 일기 내용", example = "쿼카쿼카쿼카쿼카쿼카쿼카")
        @NotNull
        String diaryContent,
        @Schema(description = "수정할 일기 날씨(CLEAR, CLOUDY, RAIN, SNOW)", example = "CLEAR")
        @NotNull
        DiaryWeather diaryWeather,
        @Schema(description = "일기 폰트", example = "INTER")
        @NotNull
        DiaryFont diaryFont,
        @Schema(description = "일기 폰트 사이즈", example = "PX30")
        @NotNull
        DiaryFontSize diaryFontSize,
        @Schema(description = "새로운 이미지", example = "[이미지 URL, 이미지 URL]")
        @Nullable
        List<String> diaryImageUrls
) {
}