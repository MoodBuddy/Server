package moodbuddy.moodbuddy.domain.diary.dto.response.emotion;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DiaryResEmotionDTO (
        String diaryEmotion,
        LocalDate diaryDate,
        String diaryComment
){
}