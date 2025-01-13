package moodbuddy.moodbuddy.domain.draftDiary.exception;

import lombok.Getter;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.global.error.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class DraftDiaryNoAccessException extends MoodBuddyException {
    public DraftDiaryNoAccessException(final ErrorCode errorCode) {
        super(errorCode);
    }
}