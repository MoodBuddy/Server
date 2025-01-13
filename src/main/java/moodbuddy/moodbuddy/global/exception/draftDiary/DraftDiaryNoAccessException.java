package moodbuddy.moodbuddy.global.exception.draftDiary;

import lombok.Getter;
import moodbuddy.moodbuddy.global.exception.ErrorCode;
import moodbuddy.moodbuddy.global.exception.MoodBuddyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class DraftDiaryNoAccessException extends MoodBuddyException {
    public DraftDiaryNoAccessException(final ErrorCode errorCode) {
        super(errorCode);
    }
}