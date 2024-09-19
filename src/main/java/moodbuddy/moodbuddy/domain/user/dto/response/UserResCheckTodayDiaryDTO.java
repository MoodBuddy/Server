package moodbuddy.moodbuddy.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class UserResCheckTodayDiaryDTO {
    private Long userId;
    private Boolean checkTodayDairy;

    public UserResCheckTodayDiaryDTO(Long userId, Boolean checkTodayDairy) {
        this.userId = userId;
        this.checkTodayDairy = checkTodayDairy;
    }
}
