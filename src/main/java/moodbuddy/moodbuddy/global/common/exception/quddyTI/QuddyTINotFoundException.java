package moodbuddy.moodbuddy.global.common.exception.quddyTI;

import lombok.Getter;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class QuddyTINotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public QuddyTINotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}