package moodbuddy.moodbuddy.domain.bookMark.exception;

import lombok.Getter;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.global.error.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BookMarkNotFoundException extends MoodBuddyException {
    public BookMarkNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}