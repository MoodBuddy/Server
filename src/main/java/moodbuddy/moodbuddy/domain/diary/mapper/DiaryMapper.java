package moodbuddy.moodbuddy.domain.diary.mapper;

import moodbuddy.moodbuddy.domain.diary.dto.response.*;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiaryMapper {
    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);

    @Mapping(source = "diaryId", target = "diaryId")
    @Mapping(source = "moodBuddyStatus", target = "moodBuddyStatus")
    DiaryResDetailDTO toResDetailDTO(Diary diary);
    DiaryResDraftFindOneDTO toResDraftFindOneDTO(Diary diary);
}
