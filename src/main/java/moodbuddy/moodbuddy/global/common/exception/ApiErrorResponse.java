package moodbuddy.moodbuddy.global.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static ApiErrorResponse from(MoodBuddyException e) {
        return new ApiErrorResponse(e.getErrorCode().getErrorCode(), e.getErrorCode().getMessage());
    }
}