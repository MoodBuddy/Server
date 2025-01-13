package moodbuddy.moodbuddy.domain.diary.dto.request.save;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record DiaryReqSaveDTO (
        @Schema(description = "일기 제목", example = "쿼카의 하루")
        @NotNull
        String diaryTitle,
        @Schema(description = "일기 날짜", example = "2023-07-02")
        @NotNull
        LocalDate diaryDate,
        @Schema(description = "일기 내용", example = "쿼카쿼카쿼카쿼카쿼카쿼카")
        @NotNull
        String diaryContent,
        @Schema(description = "일기 날씨(CLEAR, CLOUDY, RAIN, SNOW)", example = "CLEAR")
        @NotNull
        DiaryWeather diaryWeather,
        @Schema(description = "일기 폰트", example = "INTER")
        @NotNull
        DiaryFont diaryFont,
        @Schema(description = "일기 폰트 사이즈", example = "PX30")
        @NotNull
        DiaryFontSize diaryFontSize,
        @Schema(description = "일기 사진 URL 리스트", example = "[이미지 URL, 이미지 URL]")
        @Nullable
        List<String> diaryImageUrls
) {}