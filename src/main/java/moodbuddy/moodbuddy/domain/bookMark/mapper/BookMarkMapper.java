package moodbuddy.moodbuddy.domain.bookMark.mapper;

import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMarkMapper {

    BookMarkMapper INSTANCE = Mappers.getMapper(BookMarkMapper.class);

    BookMarkResToggleDTO toResToggleDTO(Boolean check);
}