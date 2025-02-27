package moodbuddy.moodbuddy.domain.draftDiary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.Tuple;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;

import java.time.LocalDate;
import java.util.List;

import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;
import static moodbuddy.moodbuddy.domain.draftDiary.domain.QDraftDiary.draftDiary;

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

    public static DraftDiaryResDetailDTO from(Tuple result) {
        return new DraftDiaryResDetailDTO(
                result.get(draftDiary.id),
                result.get(draftDiary.title),
                result.get(draftDiary.date),
                result.get(draftDiary.content),
                result.get(draftDiary.weather),
                result.get(draftDiary.font),
                result.get(draftDiary.fontSize)
        );
    }

    public void saveDraftDiaryImageUrls(List<String> imageUrls) {
        this.diaryImageUrls = imageUrls;
    }
}