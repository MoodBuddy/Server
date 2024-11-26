package moodbuddy.moodbuddy.domain.diary.mapper.draft;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DraftDiaryMapper {
    DraftDiaryMapper INSTANCE = Mappers.getMapper(DraftDiaryMapper.class);

    @Mapping(source = "diaryId", target = "diaryId")
    @Mapping(source = "moodBuddyStatus", target = "moodBuddyStatus")
    DiaryResDetailDTO toResDetailDTO(Diary diary);
}
