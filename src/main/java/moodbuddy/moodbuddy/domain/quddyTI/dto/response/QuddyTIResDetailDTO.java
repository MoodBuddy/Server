package moodbuddy.moodbuddy.domain.quddyTI.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTIStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuddyTIResDetailDTO {
    private Long userId;
    private String quddyTIYearMonth;
    private Integer diaryFrequency;
    private Integer happinessCount;
    private Integer angerCount;
    private Integer disgustCount;
    private Integer fearCount;
    private Integer neutralCount;
    private Integer sadnessCount;
    private Integer surpriseCount;
    private Integer dailyCount;
    private Integer growthCount;
    private Integer emotionCount;
    private Integer travelCount;
    private String quddyTIType ;
    private QuddyTIStatus quddyTIStatus;
}
