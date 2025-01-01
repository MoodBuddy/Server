package moodbuddy.moodbuddy.domain.diary.dto.response;

import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import java.time.LocalDate;
import java.util.List;

public record DiaryResDetailDTO(
        Long diaryId,
        String diaryTitle,
        LocalDate diaryDate,
        String diaryContent,
        DiaryWeather diaryWeather,
        DiaryEmotion diaryEmotion,
        Boolean diaryBookMark,
        DiaryFont diaryFont,
        DiaryFontSize diaryFontSize
//        List<String> diaryImages
) {}