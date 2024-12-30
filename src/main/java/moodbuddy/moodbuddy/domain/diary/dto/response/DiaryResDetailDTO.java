package moodbuddy.moodbuddy.domain.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryResDetailDTO {
    @Schema(description = "일기 고유 식별자(diaryId)", example = "1")
    private Long diaryId;
    @Schema(description = "일기 제목", example = "쿼카의 하루")
    private String diaryTitle;
    @Schema(description = "일기 날짜", example = "2023-07-02T15:30:00")
    private LocalDate diaryDate;
    @Schema(description = "일기 내용", example = "쿼카쿼카쿼카쿼카쿼카쿼카")
    private String diaryContent;
    @Schema(description = "일기 날씨(CLEAR, CLOUDY, RAIN, SNOW)", example = "CLEAR")
    private DiaryWeather diaryWeather;
    @Schema(description = "일기 감정(HAPPINESS, ANGER, DISGUST, FEAR, NEUTRAL, SADNESS, SURPRISE)", example = "HAPPY")
    private DiaryEmotion diaryEmotion;
    @Schema(description = "일기 북마크 여부", example = "ture")
    private Boolean diaryBookMarkCheck;
    @Schema(description = "일기 폰트", example = "INTER")
    private DiaryFont diaryFont;
    @Schema(description = "일기 폰트 사이즈", example = "PX30")
    private DiaryFontSize diaryFontSize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "일기 이미지 List", example = "[이미지 URL, 이미지 URL]")
    private List<String> diaryImgList;


    public DiaryResDetailDTO(Long diaryId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryEmotion diaryEmotion, Boolean diaryBookMarkCheck, DiaryFont diaryFont, DiaryFontSize diaryFontSize) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryEmotion = diaryEmotion;
        this.diaryBookMarkCheck = diaryBookMarkCheck;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
    }
    public DiaryResDetailDTO(Long diaryId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryEmotion diaryEmotion, Boolean diaryBookMarkCheck, List<String> diaryImgList, DiaryFont diaryFont, DiaryFontSize diaryFontSize) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryEmotion = diaryEmotion;
        this.diaryBookMarkCheck = diaryBookMarkCheck;
        this.diaryImgList = diaryImgList;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
    }
}
