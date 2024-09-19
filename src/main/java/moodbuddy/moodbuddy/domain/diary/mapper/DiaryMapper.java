package moodbuddy.moodbuddy.domain.diary.mapper;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.entity.Diary;
import moodbuddy.moodbuddy.domain.diary.entity.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.entity.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.entity.DiarySubject;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class DiaryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Diary toDiaryEntity(DiaryReqSaveDTO diaryReqSaveDTO, Long userId, String summary, DiarySubject diarySubject) {
        return Diary.builder()
                .diaryTitle(diaryReqSaveDTO.getDiaryTitle())
                .diaryDate(diaryReqSaveDTO.getDiaryDate())
                .diaryContent(diaryReqSaveDTO.getDiaryContent())
                .diaryWeather(diaryReqSaveDTO.getDiaryWeather())
                .diaryStatus(DiaryStatus.PUBLISHED)
                .diarySummary(summary)
                .diarySubject(diarySubject)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(diaryReqSaveDTO.getDiaryFont())
                .diaryFontSize(diaryReqSaveDTO.getDiaryFontSize())
                .build();
    }

    public static Diary toDraftEntity(DiaryReqSaveDTO diaryReqSaveDTO, Long userId) {
        return Diary.builder()
                .diaryTitle(diaryReqSaveDTO.getDiaryTitle())
                .diaryDate(diaryReqSaveDTO.getDiaryDate())
                .diaryContent(diaryReqSaveDTO.getDiaryContent())
                .diaryWeather(diaryReqSaveDTO.getDiaryWeather())
                .diaryStatus(DiaryStatus.DRAFT)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(diaryReqSaveDTO.getDiaryFont())
                .diaryFontSize(diaryReqSaveDTO.getDiaryFontSize())
                .build();
    }

    public static DiaryResDetailDTO toDetailDTO(Diary diary) {
        return modelMapper.map(diary, DiaryResDetailDTO.class);
    }

}
