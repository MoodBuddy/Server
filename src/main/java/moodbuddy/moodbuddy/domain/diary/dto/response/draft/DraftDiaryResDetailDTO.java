package moodbuddy.moodbuddy.domain.diary.dto.response.draft;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFont;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DraftDiaryResDetailDTO {
    private Long diaryId;
    private Long userId;
    private String diaryTitle;
    private LocalDate diaryDate;
    private String diaryContent;
    private DiaryWeather diaryWeather;
    private DiaryStatus diaryStatus;
    private DiaryFont diaryFont;
    private DiaryFontSize diaryFontSize;
    private MoodBuddyStatus moodBuddyStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> diaryImageUrls;

    public DraftDiaryResDetailDTO(Long diaryId, Long userId, String diaryTitle, LocalDate diaryDate, String diaryContent, DiaryWeather diaryWeather, DiaryStatus diaryStatus, DiaryFont diaryFont, DiaryFontSize diaryFontSize, MoodBuddyStatus moodBuddyStatus) {
        this.diaryId = diaryId;
        this.userId = userId;
        this.diaryTitle = diaryTitle;
        this.diaryDate = diaryDate;
        this.diaryContent = diaryContent;
        this.diaryWeather = diaryWeather;
        this.diaryStatus = diaryStatus;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
        this.moodBuddyStatus = moodBuddyStatus;
        this.diaryImageUrls = List.of();
    }

    public void saveDiaryImageUrls(List<String> imageUrls) {
        this.diaryImageUrls = imageUrls;
    }
}