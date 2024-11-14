package moodbuddy.moodbuddy.domain.diary.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DiaryResDraftFindOneDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-14T17:21:34+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Homebrew)"
)
@Component
public class DiaryMapperImpl implements DiaryMapper {

    @Override
    public DiaryResDetailDTO toResDetailDTO(Diary diary) {
        if ( diary == null ) {
            return null;
        }

        DiaryResDetailDTO.DiaryResDetailDTOBuilder diaryResDetailDTO = DiaryResDetailDTO.builder();

        diaryResDetailDTO.diaryId( diary.getDiaryId() );
        diaryResDetailDTO.moodBuddyStatus( diary.getMoodBuddyStatus() );
        diaryResDetailDTO.userId( diary.getUserId() );
        diaryResDetailDTO.diaryTitle( diary.getDiaryTitle() );
        diaryResDetailDTO.diaryDate( diary.getDiaryDate() );
        diaryResDetailDTO.diaryContent( diary.getDiaryContent() );
        diaryResDetailDTO.diaryWeather( diary.getDiaryWeather() );
        diaryResDetailDTO.diaryEmotion( diary.getDiaryEmotion() );
        diaryResDetailDTO.diaryStatus( diary.getDiaryStatus() );
        diaryResDetailDTO.diarySummary( diary.getDiarySummary() );
        diaryResDetailDTO.diarySubject( diary.getDiarySubject() );
        diaryResDetailDTO.diaryBookMarkCheck( diary.getDiaryBookMarkCheck() );
        diaryResDetailDTO.diaryFont( diary.getDiaryFont() );
        diaryResDetailDTO.diaryFontSize( diary.getDiaryFontSize() );

        return diaryResDetailDTO.build();
    }

    @Override
    public DiaryResDraftFindOneDTO toResDraftFindOneDTO(Diary diary) {
        if ( diary == null ) {
            return null;
        }

        Long diaryId = null;
        Long userId = null;
        LocalDate diaryDate = null;
        DiaryStatus diaryStatus = null;

        diaryId = diary.getDiaryId();
        userId = diary.getUserId();
        diaryDate = diary.getDiaryDate();
        diaryStatus = diary.getDiaryStatus();

        DiaryResDraftFindOneDTO diaryResDraftFindOneDTO = new DiaryResDraftFindOneDTO( diaryId, userId, diaryDate, diaryStatus );

        return diaryResDraftFindOneDTO;
    }
}
