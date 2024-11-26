package moodbuddy.moodbuddy.domain.diary.mapper.image;

import javax.annotation.processing.Generated;
import moodbuddy.moodbuddy.domain.diary.dto.response.image.DiaryImageResURLDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T19:43:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Homebrew)"
)
@Component
public class DiaryImageMapperImpl implements DiaryImageMapper {

    @Override
    public DiaryImageResURLDTO toResURLDTO(String diaryImageURL) {
        if ( diaryImageURL == null ) {
            return null;
        }

        String diaryImageURL1 = null;

        diaryImageURL1 = diaryImageURL;

        DiaryImageResURLDTO diaryImageResURLDTO = new DiaryImageResURLDTO( diaryImageURL1 );

        return diaryImageResURLDTO;
    }
}
