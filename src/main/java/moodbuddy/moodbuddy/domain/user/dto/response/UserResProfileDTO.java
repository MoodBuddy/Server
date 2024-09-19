package moodbuddy.moodbuddy.domain.user.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserResProfileDTO {
    String url;
    String profileComment;
    String nickname;
    Boolean alarm;
    String alarmTime;
    String phoneNumber;
    Boolean gender;
    String birthday;
}


