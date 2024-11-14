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
    @Schema(description = "사용자 고유 식별자(userId)", example = "1")
    private Long userId;
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
    @Schema(description = "일기 상태(DRAFT, PUBLISHED", example = "DRAFT")
    private DiaryStatus diaryStatus;
    @Schema(description = "일기 요약", example = "쿼카의 하루에 대한 일기입니다.")
    private String diarySummary;
    @Schema(description = "일기 주제", example = "쿼카의 하루에 대한 일기 주제입니다.")
    private DiarySubject diarySubject;
    @Schema(description = "일기 북마크 여부", example = "ture")
    private Boolean diaryBookMarkCheck;
    @Schema(description = "일기 폰트", example = "INTER")
    private DiaryFont diaryFont;
    @Schema(description = "일기 폰트 사이즈", example = "PX30")
    private DiaryFontSize diaryFontSize;
    @Schema(description = "일기 상태", example = "ACTIVE")
    private MoodBuddyStatus moodBuddyStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL) // 굳이 필요하지 않은 경우가 있음.
    @Schema(description = "일기 이미지 List", example = "[이미지 URL, 이미지 URL]")
    private List<String> diaryImgList;


    public DiaryResDetailDTO(Long diaryId, Long userId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryEmotion diaryEmotion, DiaryStatus diaryStatus, String diarySummary, DiarySubject diarySubject, Boolean diaryBookMarkCheck, DiaryFont diaryFont, DiaryFontSize diaryFontSize, MoodBuddyStatus moodBuddyStatus) {
        this.diaryId = diaryId;
        this.userId = userId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryEmotion = diaryEmotion;
        this.diaryStatus = diaryStatus;
        this.diarySummary = diarySummary;
        this.diarySubject = diarySubject;
        this.diaryBookMarkCheck = diaryBookMarkCheck;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
        this.moodBuddyStatus = moodBuddyStatus;
    }
    public DiaryResDetailDTO(Long diaryId, Long userId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryEmotion diaryEmotion, DiaryStatus diaryStatus, String diarySummary, DiarySubject diarySubject, Boolean diaryBookMarkCheck, List<String> diaryImgList, DiaryFont diaryFont, DiaryFontSize diaryFontSize, MoodBuddyStatus moodBuddyStatus) {
        this.diaryId = diaryId;
        this.userId = userId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryEmotion = diaryEmotion;
        this.diaryStatus = diaryStatus;
        this.diarySummary = diarySummary;
        this.diarySubject = diarySubject;
        this.diaryBookMarkCheck = diaryBookMarkCheck;
        this.diaryImgList = diaryImgList;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
        this.moodBuddyStatus = moodBuddyStatus;
    }
}
