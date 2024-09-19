package moodbuddy.moodbuddy.domain.user.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDiaryNumsDTO {
    private String month;
    private Integer nums;
}
