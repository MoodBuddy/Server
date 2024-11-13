package moodbuddy.moodbuddy.global.common.exception.database;

import lombok.Getter;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class DatabaseNullOrEmptyException extends MoodBuddyException {
    public DatabaseNullOrEmptyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DatabaseNullOrEmptyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}

