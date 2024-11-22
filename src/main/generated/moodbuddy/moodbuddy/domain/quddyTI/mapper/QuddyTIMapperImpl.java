package moodbuddy.moodbuddy.domain.quddyTI.mapper;

import javax.annotation.processing.Generated;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-22T19:45:48+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Homebrew)"
)
@Component
public class QuddyTIMapperImpl implements QuddyTIMapper {

    @Override
    public QuddyTIResDetailDTO toResDetailDTO(QuddyTI quddyTI) {
        if ( quddyTI == null ) {
            return null;
        }

        QuddyTIResDetailDTO.QuddyTIResDetailDTOBuilder quddyTIResDetailDTO = QuddyTIResDetailDTO.builder();

        quddyTIResDetailDTO.userId( quddyTI.getUserId() );
        quddyTIResDetailDTO.quddyTIYear( quddyTI.getQuddyTIYear() );
        quddyTIResDetailDTO.quddyTIMonth( quddyTI.getQuddyTIMonth() );
        quddyTIResDetailDTO.diaryFrequency( quddyTI.getDiaryFrequency() );
        quddyTIResDetailDTO.happinessCount( quddyTI.getHappinessCount() );
        quddyTIResDetailDTO.angerCount( quddyTI.getAngerCount() );
        quddyTIResDetailDTO.disgustCount( quddyTI.getDisgustCount() );
        quddyTIResDetailDTO.fearCount( quddyTI.getFearCount() );
        quddyTIResDetailDTO.neutralCount( quddyTI.getNeutralCount() );
        quddyTIResDetailDTO.sadnessCount( quddyTI.getSadnessCount() );
        quddyTIResDetailDTO.surpriseCount( quddyTI.getSurpriseCount() );
        quddyTIResDetailDTO.dailyCount( quddyTI.getDailyCount() );
        quddyTIResDetailDTO.growthCount( quddyTI.getGrowthCount() );
        quddyTIResDetailDTO.emotionCount( quddyTI.getEmotionCount() );
        quddyTIResDetailDTO.travelCount( quddyTI.getTravelCount() );
        quddyTIResDetailDTO.quddyTIType( quddyTI.getQuddyTIType() );
        quddyTIResDetailDTO.moodBuddyStatus( quddyTI.getMoodBuddyStatus() );

        return quddyTIResDetailDTO.build();
    }
}
