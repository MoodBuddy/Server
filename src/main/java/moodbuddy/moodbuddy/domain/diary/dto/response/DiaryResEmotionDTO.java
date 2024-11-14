package moodbuddy.moodbuddy.domain.diary.dto.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DiaryResEmotionDTO {
    private String emotion;
    private LocalDate diaryDate;
    private String comment;
}