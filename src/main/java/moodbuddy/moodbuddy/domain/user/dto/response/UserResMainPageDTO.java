package moodbuddy.moodbuddy.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.entity.DiaryEmotion;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResMainPageDTO {
    private String nickname;
    private String userBirth;
    private String profileComment;
    private String profileImgURL;
    private Integer userCurDiaryNums;
    private DiaryEmotion diaryEmotion;
    private Integer maxEmotionNum;
}
