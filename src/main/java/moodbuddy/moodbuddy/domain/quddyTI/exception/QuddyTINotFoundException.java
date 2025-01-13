package moodbuddy.moodbuddy.domain.quddyTI.exception;

import lombok.Getter;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.global.error.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class QuddyTINotFoundException extends MoodBuddyException {
    public QuddyTINotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}