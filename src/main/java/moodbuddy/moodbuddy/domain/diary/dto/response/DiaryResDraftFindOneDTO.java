package moodbuddy.moodbuddy.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryFont;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryFontSize;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
public class DiaryResDraftFindOneDTO {
    @Schema(description = "일기 고유 식별자(diaryId)", example = "1")
    private Long diaryId;
    @Schema(description = "사용자 고유 식별자(userId)", example = "2")
    private Long userId;
    @Schema(description = "일기 날짜", example = "2023-07-02T15:30:00")
    private LocalDate diaryDate;
    @Schema(description = "일기 상태(DRAFT, PUBLISHED", example = "DRAFT")
    private DiaryStatus diaryStatus;
    @Schema(description = "일기 폰트", example = "INTER")
    private DiaryFont diaryFont;
    @Schema(description = "일기 폰트 사이즈", example = "PX30")
    private DiaryFontSize diaryFontSize;

    public DiaryResDraftFindOneDTO(Long diaryId, Long userId, LocalDate diaryDate, DiaryStatus diaryStatus, DiaryFont diaryFont, DiaryFontSize diaryFontSize) {
        this.diaryId = diaryId;
        this.userId = userId;
        this.diaryDate = diaryDate;
        this.diaryStatus = diaryStatus;
        this.diaryFont = diaryFont;
        this.diaryFontSize = diaryFontSize;
    }
}
