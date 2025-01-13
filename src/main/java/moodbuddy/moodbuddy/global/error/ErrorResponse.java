package moodbuddy.moodbuddy.global.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse from(MoodBuddyException e) {
        return new ErrorResponse(e.getErrorCode().getErrorCode(), e.getErrorCode().getMessage());
    }
}