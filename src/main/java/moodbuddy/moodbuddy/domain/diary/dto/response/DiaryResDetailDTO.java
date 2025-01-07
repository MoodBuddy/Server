package moodbuddy.moodbuddy.domain.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryResDetailDTO {
    private Long diaryId;
    private String diaryTitle;
    private LocalDate diaryDate;
    private String diaryContent;
    private DiaryWeather diaryWeather;
    private DiaryEmotion diaryEmotion;
    private DiaryFont diaryFont;
    private DiaryFontSize diaryFontSize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> diaryImageUrls;


    public DiaryResDetailDTO(Long diaryId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryEmotion diaryEmotion, DiaryFont diaryFont, DiaryFontSize diaryFontSize) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryEmotion = diaryEmotion;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
        this.diaryImageUrls = List.of();
    }

    public void saveDiaryImageUrls(List<String> imageUrls) {
        this.diaryImageUrls = imageUrls;
    }
}