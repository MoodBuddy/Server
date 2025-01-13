package moodbuddy.moodbuddy.domain.letter.exception;

import lombok.Getter;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.global.error.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class LetterNotFoundByIdAndUserIdException extends MoodBuddyException {
    public LetterNotFoundByIdAndUserIdException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
