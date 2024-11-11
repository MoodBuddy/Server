package moodbuddy.moodbuddy.domain.diary.mapper;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.domain.DiarySubject;
import org.modelmapper.ModelMapper;

public class DiaryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Diary toDiaryEntity(DiaryReqSaveDTO diaryReqSaveDTO, Long userId, String summary, DiarySubject diarySubject) {
        return Diary.builder()
                .diaryTitle(diaryReqSaveDTO.diaryTitle())
                .diaryDate(diaryReqSaveDTO.diaryDate())
                .diaryContent(diaryReqSaveDTO.diaryContent())
                .diaryWeather(diaryReqSaveDTO.diaryWeather())
                .diaryStatus(DiaryStatus.PUBLISHED)
                .diarySummary(summary)
                .diarySubject(diarySubject)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(diaryReqSaveDTO.diaryFont())
                .diaryFontSize(diaryReqSaveDTO.diaryFontSize())
                .build();
    }

    public static Diary toDraftEntity(DiaryReqSaveDTO diaryReqSaveDTO, Long userId) {
        return Diary.builder()
                .diaryTitle(diaryReqSaveDTO.diaryTitle())
                .diaryDate(diaryReqSaveDTO.diaryDate())
                .diaryContent(diaryReqSaveDTO.diaryContent())
                .diaryWeather(diaryReqSaveDTO.diaryWeather())
                .diaryStatus(DiaryStatus.DRAFT)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(diaryReqSaveDTO.diaryFont())
                .diaryFontSize(diaryReqSaveDTO.diaryFontSize())
                .build();
    }

    public static DiaryResDetailDTO toDetailDTO(Diary diary) {
        return modelMapper.map(diary, DiaryResDetailDTO.class);
    }

}
