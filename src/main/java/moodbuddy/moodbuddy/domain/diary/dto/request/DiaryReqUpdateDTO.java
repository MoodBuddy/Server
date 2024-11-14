package moodbuddy.moodbuddy.domain.diary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFont;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;

import java.time.LocalDate;

public record DiaryReqUpdateDTO (
        @Schema(description = "수정할 일기 고유 식별자(diaryId)", example = "1")
        Long diaryId,
        @Schema(description = "수정할 일기 제목", example = "쿼카의 하카")
        String diaryTitle,
        //TODO 일기 날짜 수정은 기획과 논의할 필요가 있음
        @Schema(description = "수정할 일기 날짜", example = "2023-07-02T15:30:00")
        LocalDate diaryDate,
        @Schema(description = "수정할 일기 내용", example = "쿼카쿼카쿼카쿼카쿼카쿼카")
        String diaryContent,
        @Schema(description = "수정할 일기 날씨(CLEAR, CLOUDY, RAIN, SNOW)", example = "CLEAR")
        DiaryWeather diaryWeather,
        @Schema(description = "수정할 일기 상태(DRAFT, PUBLISHED)", example = "DRAFT")
        DiaryStatus diaryStatus,
        @Schema(description = "일기 폰트", example = "INTER")
        DiaryFont diaryFont,
        @Schema(description = "일기 폰트 사이즈", example = "PX30")
        DiaryFontSize diaryFontSize
) {
}
