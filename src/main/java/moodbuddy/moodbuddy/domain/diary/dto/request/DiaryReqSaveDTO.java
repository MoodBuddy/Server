package moodbuddy.moodbuddy.domain.diary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryFont;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryWeather;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record DiaryReqSaveDTO (
        @Schema(description = "일기 제목", example = "쿼카의 하루")
        String diaryTitle,
        @Schema(description = "일기 날짜", example = "2023-07-02")
        LocalDate diaryDate,
        @Schema(description = "일기 내용", example = "쿼카쿼카쿼카쿼카쿼카쿼카")
        String diaryContent,
        @Schema(description = "일기 날씨(CLEAR, CLOUDY, RAIN, SNOW)", example = "CLEAR")
        DiaryWeather diaryWeather,
        @Schema(description = "일기 이미지 List", example = "[\"image1.png\", \"image2.png\"]")
        List<MultipartFile> diaryImgList,
        @Schema(description = "일기 폰트", example = "INTER")
        DiaryFont diaryFont,
        @Schema(description = "일기 폰트 사이즈", example = "PX30")
        DiaryFontSize diaryFontSize )
{
}
