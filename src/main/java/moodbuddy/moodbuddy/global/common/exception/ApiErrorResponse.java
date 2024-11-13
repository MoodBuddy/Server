package moodbuddy.moodbuddy.global.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {
    private String error;
    private String message;

    public ApiErrorResponse(String error,String message) {
        super();
        this.error = error;
        this.message = message;
    }

    public static ApiErrorResponse from(MoodBuddyException e) {
        return new ApiErrorResponse(e.getErrorCode().getErrorCode(), e.getErrorCode().getMessage());
    }
}