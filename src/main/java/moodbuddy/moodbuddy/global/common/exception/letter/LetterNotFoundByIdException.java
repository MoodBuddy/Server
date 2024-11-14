package moodbuddy.moodbuddy.global.common.exception.letter;


import lombok.Getter;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class LetterNotFoundByIdException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Long letterId;
    public LetterNotFoundByIdException(final Long letterId, final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.letterId = letterId;
    }
}
