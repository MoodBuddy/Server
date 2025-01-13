package moodbuddy.moodbuddy.global.error;

import lombok.Getter;

@Getter
public class MoodBuddyException extends RuntimeException {
    private final ErrorCode errorCode;

    public MoodBuddyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MoodBuddyException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
