package moodbuddy.moodbuddy.domain.quddyTI.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
