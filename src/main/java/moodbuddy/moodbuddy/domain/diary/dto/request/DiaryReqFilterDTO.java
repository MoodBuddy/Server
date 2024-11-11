package moodbuddy.moodbuddy.domain.diary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.DiarySubject;

@Builder
public record DiaryReqFilterDTO (
        @Schema(description = "검색하고 싶은 키워드", example = "쿼카")
        String keyWord,
        @Schema(description = "검색하고 싶은 년도", example = "2024")
        Integer year,
        @Schema(description = "검색하고 싶은 월", example = "7")
        Integer month,
        @Schema(description = "검색하고 싶은 감정(HAPPY, ANGRY, AVERSION, SURPRISED, CALMNESS, DEPRESSION, FEAR)", example = "HAPPY")
        DiaryEmotion diaryEmotion,
        @Schema(description = "검색하고 싶은 주제(DAILY, GROWTH, EMOTION, TRAVEL)", example = "DAILY")
        DiarySubject diarySubject
) {
}
