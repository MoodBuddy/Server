package moodbuddy.moodbuddy.domain.diary.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DiaryResEmotionDTO (
        String emotion,
        LocalDate diaryDate,
        String comment
){
}