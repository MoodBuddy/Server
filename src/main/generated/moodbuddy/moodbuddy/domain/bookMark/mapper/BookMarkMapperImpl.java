package moodbuddy.moodbuddy.domain.bookMark.mapper;

import javax.annotation.processing.Generated;
import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-14T00:37:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Homebrew)"
)
@Component
public class BookMarkMapperImpl implements BookMarkMapper {

    @Override
    public BookMarkResToggleDTO toResToggleDTO(Boolean check) {
        if ( check == null ) {
            return null;
        }

        boolean bookmarked = false;

        BookMarkResToggleDTO bookMarkResToggleDTO = new BookMarkResToggleDTO( bookmarked );

        return bookMarkResToggleDTO;
    }
}
