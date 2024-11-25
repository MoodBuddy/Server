package moodbuddy.moodbuddy.domain.profile.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProfileResDetailDTO {
    String url;
    String profileComment;
    String nickname;
    Boolean alarm;
    String alarmTime;
    String phoneNumber;
    Boolean gender;
    String birthday;
}


