package moodbuddy.moodbuddy.domain.diary.mapper.image;

import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiaryImageMapper {
    DiaryImageMapper INSTANCE = Mappers.getMapper(DiaryImageMapper.class);

    @Mapping(source = "diaryImageURL", target = "diaryImageURL")
    DiaryImageResURLDTO toResURLDTO(String diaryImageURL);
}
