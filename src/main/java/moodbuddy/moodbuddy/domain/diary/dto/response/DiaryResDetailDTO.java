package moodbuddy.moodbuddy.domain.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import java.time.LocalDate;
import java.util.List;
import static moodbuddy.moodbuddy.domain.diary.domain.QDiary.diary;

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

    public static DiaryResDetailDTO from(Tuple result) {
        return new DiaryResDetailDTO(
                result.get(diary.id),
                result.get(diary.title),
                result.get(diary.date),
                result.get(diary.content),
                result.get(diary.weather),
                result.get(diary.emotion),
                result.get(diary.font),
                result.get(diary.fontSize)
        );
    }

    public void saveDiaryImageUrls(List<String> imageUrls) {
        this.diaryImageUrls = imageUrls;
    }
}