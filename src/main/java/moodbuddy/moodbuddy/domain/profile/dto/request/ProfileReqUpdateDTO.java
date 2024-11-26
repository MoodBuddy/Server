package moodbuddy.moodbuddy.domain.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProfileReqUpdateDTO {
    String profileComment;
    Boolean alarm;
    @Schema(description = "알림, HH:mm 형식")
    String alarmTime;
    @Schema(description = "사용자 전화번호, -을 제외한 01012345678 형식")
    String phoneNumber;
    @Schema(description = "업로드 할 이미지 url", example = "이미지 url")
    String profileImageUrl;
    String nickname;
    Boolean gender;
    @Schema(description = "생일, YYYY-mm-dd 형식")
    String birthday;
}
