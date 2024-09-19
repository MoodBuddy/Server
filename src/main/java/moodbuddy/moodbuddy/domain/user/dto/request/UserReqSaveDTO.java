package moodbuddy.moodbuddy.domain.user.dto.request;

import lombok.Data;

@Data
public class UserReqSaveDTO {
    private String nickname;
    private Boolean alarm;
    private String alarmTime;
    private Boolean letterAlarm;
    private String phoneNumber;
    private String birthday;
    private Boolean gender;
}
