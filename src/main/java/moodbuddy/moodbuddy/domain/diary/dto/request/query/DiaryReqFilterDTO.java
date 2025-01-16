package moodbuddy.moodbuddy.domain.diary.dto.request.query;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

import javax.annotation.Nullable;

public record DiaryReqFilterDTO(
        @Nullable String keyWord,
        @Nullable Integer year,
        @Nullable Integer month,
        @Nullable DiaryEmotion diaryEmotion,
        @Nullable DiarySubject diarySubject
) {
        public static DiaryReqFilterDTO of(
                String keyWord,
                Integer year,
                Integer month,
                DiaryEmotion diaryEmotion,
                DiarySubject diarySubject) {

                return new DiaryReqFilterDTO(
                        keyWord,
                        year,
                        month,
                        diaryEmotion,
                        diarySubject
                );
        }
}