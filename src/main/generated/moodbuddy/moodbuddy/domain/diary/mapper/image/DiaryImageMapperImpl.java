package moodbuddy.moodbuddy.domain.diary.mapper.image;

import javax.annotation.processing.Generated;
import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T17:14:38+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Homebrew)"
)
@Component
public class DiaryImageMapperImpl implements DiaryImageMapper {

    @Override
    public DiaryImageResURLDTO toResURLDTO(String diaryImageUrl) {
        if ( diaryImageUrl == null ) {
            return null;
        }

        String diaryImageURL = null;

        DiaryImageResURLDTO diaryImageResURLDTO = new DiaryImageResURLDTO( diaryImageURL );

        return diaryImageResURLDTO;
    }
}
