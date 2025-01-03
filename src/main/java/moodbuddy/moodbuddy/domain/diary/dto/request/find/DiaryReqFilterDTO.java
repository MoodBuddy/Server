package moodbuddy.moodbuddy.domain.diary.dto.request.find;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;

public record DiaryReqFilterDTO(
        String keyWord,
        Integer year,
        Integer month,
        DiaryEmotion diaryEmotion,
        DiarySubject diarySubject
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