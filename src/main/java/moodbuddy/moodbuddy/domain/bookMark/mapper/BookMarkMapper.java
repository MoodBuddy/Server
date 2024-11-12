package moodbuddy.moodbuddy.domain.bookMark.mapper;

import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMarkMapper {

    BookMarkMapper INSTANCE = Mappers.getMapper(BookMarkMapper.class);

    @Mapping(target = "bookmarked", source = "diary.diaryBookMarkCheck")
    BookMarkResToggleDTO toResToggleDTO(BookMark bookMark);
}