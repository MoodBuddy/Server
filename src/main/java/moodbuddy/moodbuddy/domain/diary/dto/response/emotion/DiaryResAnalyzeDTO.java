package moodbuddy.moodbuddy.domain.diary.dto.response.emotion;

import lombok.Builder;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

import java.time.LocalDate;

@Builder
public record DiaryResAnalyzeDTO(
        DiarySubject diarySubject,
        String diarySummary,
        DiaryEmotion diaryEmotion,
        String diaryComment
){
}