package moodbuddy.moodbuddy.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MoodBuddyException.class)
    public ResponseEntity<ErrorResponse> handleDateRoadException(final MoodBuddyException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorResponse.from(e));
    }
}