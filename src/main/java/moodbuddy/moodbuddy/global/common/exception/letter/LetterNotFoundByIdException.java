package moodbuddy.moodbuddy.global.common.exception.letter;


import lombok.Getter;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class LetterNotFoundByIdException extends MoodBuddyException {
    public LetterNotFoundByIdException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
