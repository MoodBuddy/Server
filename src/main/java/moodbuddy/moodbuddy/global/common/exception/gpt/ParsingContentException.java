package moodbuddy.moodbuddy.global.common.exception.gpt;

import lombok.Getter;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class ParsingContentException extends MoodBuddyException {
    public ParsingContentException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
