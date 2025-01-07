package moodbuddy.moodbuddy.domain.quddyTI.dto.response;

import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;

public record QuddyTIResDetailDTO(
        Long userId,
        String quddyTIYear,
        String quddyTIMonth,
        Integer diaryFrequency,
        Integer happinessCount,
        Integer angerCount,
        Integer disgustCount,
        Integer fearCount,
        Integer neutralCount,
        Integer sadnessCount,
        Integer surpriseCount,
        Integer dailyCount,
        Integer growthCount,
        Integer emotionCount,
        Integer travelCount,
        String quddyTI,
        MoodBuddyStatus moodBuddyStatus
) {
}
