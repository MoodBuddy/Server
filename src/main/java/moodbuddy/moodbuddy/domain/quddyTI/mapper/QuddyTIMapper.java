package moodbuddy.moodbuddy.domain.quddyTI.mapper;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuddyTIMapper {
    QuddyTIMapper INSTANCE = Mappers.getMapper(QuddyTIMapper.class);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "quddyTIYear", source = "year")
    @Mapping(target = "quddyTIMonth", source = "month")
    @Mapping(target = "diaryFrequency", source = "diaryFrequency")
    @Mapping(target = "happinessCount", source = "happinessCount")
    @Mapping(target = "angerCount", source = "angerCount")
    @Mapping(target = "disgustCount", source = "disgustCount")
    @Mapping(target = "fearCount", source = "fearCount")
    @Mapping(target = "neutralCount", source = "neutralCount")
    @Mapping(target = "sadnessCount", source = "sadnessCount")
    @Mapping(target = "surpriseCount", source = "surpriseCount")
    @Mapping(target = "dailyCount", source = "dailyCount")
    @Mapping(target = "growthCount", source = "growthCount")
    @Mapping(target = "emotionCount", source = "emotionCount")
    @Mapping(target = "travelCount", source = "travelCount")
    @Mapping(target = "quddyTI", source = "quddyTI")
    @Mapping(target = "moodBuddyStatus", source = "moodBuddyStatus")
    QuddyTIResDetailDTO toResDetailDTO(QuddyTI quddyTI);
}
