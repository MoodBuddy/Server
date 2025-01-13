package moodbuddy.moodbuddy.global.exception.diaryImage;

import lombok.Getter;
import moodbuddy.moodbuddy.global.exception.ErrorCode;
import moodbuddy.moodbuddy.global.exception.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class DiaryImageNotFoundException extends MoodBuddyException {
    public DiaryImageNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}