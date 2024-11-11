package moodbuddy.moodbuddy.domain.diary.mapper;

import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiarySubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiaryMapper {
    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);

    @Mapping(target = "diaryStatus", constant = "PUBLISHED")
    @Mapping(target = "diarySummary", source = "summary")
    @Mapping(target = "diarySubject", source = "diarySubject")
    @Mapping(target = "userId", source = "userId")
    Diary toDiaryEntity(DiaryReqSaveDTO diaryReqSaveDTO, Long userId, String summary, DiarySubject diarySubject);

    @Mapping(target = "diaryStatus", constant = "DRAFT")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "diaryBookMarkCheck", constant = "false")
    Diary toDraftEntity(DiaryReqSaveDTO diaryReqSaveDTO, Long userId);

    DiaryResDetailDTO toResDetailDTO(Diary diary);

    DiaryResDraftFindOneDTO toResDraftFindOneDTO(Diary diary);

    @Mapping(source = "emotion", target = "emotion")
    @Mapping(source = "diaryDate", target = "diaryDate")
    @Mapping(source = "comment", target = "comment")
    DiaryResEmotionDTO toResEmotionDTO(Diary diary);
}
