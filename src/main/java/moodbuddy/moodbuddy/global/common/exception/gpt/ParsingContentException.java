package moodbuddy.moodbuddy.global.common.exception.gpt;

import lombok.Getter;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class ParsingContentException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String content;

    public ParsingContentException(ErrorCode errorCode, String content) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.content = content;
    }
}
