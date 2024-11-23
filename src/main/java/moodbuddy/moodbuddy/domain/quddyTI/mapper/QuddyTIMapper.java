package moodbuddy.moodbuddy.domain.quddyTI.mapper;

import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTIStatus;
import org.modelmapper.ModelMapper;

public class QuddyTIMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static QuddyTIResDetailDTO toQuddyTIResDetailDTO(QuddyTI quddyTI) {
        return modelMapper.map(quddyTI, QuddyTIResDetailDTO.class);
    }

    // QuddyTI 생성
    public static QuddyTI toQuddyTIEntity(Long userId, String quddyTIYearMonth) {
        return QuddyTI.builder()
                .userId(userId)
                .happinessCount(0)
                .angerCount(0)
                .disgustCount(0)
                .fearCount(0)
                .neutralCount(0)
                .sadnessCount(0)
                .surpriseCount(0)
                .dailyCount(0)
                .growthCount(0)
                .emotionCount(0)
                .travelCount(0)
                .quddyTIType(null)
                .quddyTIStatus(QuddyTIStatus.CREATING)
                .quddyTIYearMonth(quddyTIYearMonth)
                .diaryFrequency(0)
                .build();
    }
}

