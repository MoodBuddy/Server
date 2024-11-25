package moodbuddy.moodbuddy.domain.quddyTI.mapper;

import javax.annotation.processing.Generated;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T02:00:56+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Homebrew)"
)
@Component
public class QuddyTIMapperImpl implements QuddyTIMapper {

    @Override
    public QuddyTIResDetailDTO toResDetailDTO(QuddyTI quddyTI) {
        if ( quddyTI == null ) {
            return null;
        }

        Long userId = null;
        String quddyTIYear = null;
        String quddyTIMonth = null;
        Integer diaryFrequency = null;
        Integer happinessCount = null;
        Integer angerCount = null;
        Integer disgustCount = null;
        Integer fearCount = null;
        Integer neutralCount = null;
        Integer sadnessCount = null;
        Integer surpriseCount = null;
        Integer dailyCount = null;
        Integer growthCount = null;
        Integer emotionCount = null;
        Integer travelCount = null;
        String quddyTIType = null;
        MoodBuddyStatus moodBuddyStatus = null;

        userId = quddyTI.getUserId();
        quddyTIYear = quddyTI.getQuddyTIYear();
        quddyTIMonth = quddyTI.getQuddyTIMonth();
        diaryFrequency = quddyTI.getDiaryFrequency();
        happinessCount = quddyTI.getHappinessCount();
        angerCount = quddyTI.getAngerCount();
        disgustCount = quddyTI.getDisgustCount();
        fearCount = quddyTI.getFearCount();
        neutralCount = quddyTI.getNeutralCount();
        sadnessCount = quddyTI.getSadnessCount();
        surpriseCount = quddyTI.getSurpriseCount();
        dailyCount = quddyTI.getDailyCount();
        growthCount = quddyTI.getGrowthCount();
        emotionCount = quddyTI.getEmotionCount();
        travelCount = quddyTI.getTravelCount();
        quddyTIType = quddyTI.getQuddyTIType();
        moodBuddyStatus = quddyTI.getMoodBuddyStatus();

        QuddyTIResDetailDTO quddyTIResDetailDTO = new QuddyTIResDetailDTO( userId, quddyTIYear, quddyTIMonth, diaryFrequency, happinessCount, angerCount, disgustCount, fearCount, neutralCount, sadnessCount, surpriseCount, dailyCount, growthCount, emotionCount, travelCount, quddyTIType, moodBuddyStatus );

        return quddyTIResDetailDTO;
    }
}
