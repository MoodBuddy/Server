package moodbuddy.moodbuddy.domain.diary.mapper;

import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiaryMapper {
    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);

    DiaryResDetailDTO toResDetailDTO(Diary diary);

    DiaryResDraftFindOneDTO toResDraftFindOneDTO(Diary diary);

    @Mapping(source = "emotion", target = "emotion")
    @Mapping(source = "diaryDate", target = "diaryDate")
    @Mapping(source = "comment", target = "comment")
    DiaryResEmotionDTO toResEmotionDTO(Diary diary);
}
