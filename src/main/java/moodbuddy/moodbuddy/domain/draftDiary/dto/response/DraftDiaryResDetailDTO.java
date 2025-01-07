package moodbuddy.moodbuddy.domain.draftDiary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DraftDiaryResDetailDTO {
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

    public DraftDiaryResDetailDTO(Long diaryId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryFont diaryFont, DiaryFontSize diaryFontSize) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
        this.diaryImageUrls = List.of();
    }

    public void saveDraftDiaryImageUrls(List<String> imageUrls) {
        this.diaryImageUrls = imageUrls;
    }
}