package moodbuddy.moodbuddy.domain.diary.dto.request.update;

import io.swagger.v3.oas.annotations.media.Schema;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;

import javax.annotation.Nullable;
import java.util.List;

public record DiaryReqUpdateDTO (
        @Schema(description = "수정할 일기 고유 식별자(draftDiaryId)", example = "1")
        Long diaryId,
        @Schema(description = "수정할 일기 제목", example = "쿼카의 하카")
        String diaryTitle,
        @Schema(description = "수정할 일기 내용", example = "쿼카쿼카쿼카쿼카쿼카쿼카")
        String diaryContent,
        @Schema(description = "수정할 일기 날씨(CLEAR, CLOUDY, RAIN, SNOW)", example = "CLEAR")
        DiaryWeather diaryWeather,
        @Schema(description = "일기 폰트", example = "INTER")
        DiaryFont diaryFont,
        @Schema(description = "일기 폰트 사이즈", example = "PX30")
        DiaryFontSize diaryFontSize,
        @Schema(description = "새로운 이미지", example = "[이미지 URL, 이미지 URL]")
        @Nullable
        List<String> diaryImageUrls
) {
}