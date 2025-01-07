package moodbuddy.moodbuddy.global.common.exception;

import moodbuddy.moodbuddy.global.common.exception.bookMark.BookMarkNotFoundException;
import moodbuddy.moodbuddy.global.common.exception.database.DatabaseNullOrEmptyException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryInsufficientException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNoAccessException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryTodayExistingException;
import moodbuddy.moodbuddy.global.common.exception.gpt.ParsingContentException;
import moodbuddy.moodbuddy.global.common.exception.letter.LetterNotFoundByIdAndUserIdException;
import moodbuddy.moodbuddy.global.common.exception.letter.LetterNotFoundByIdException;
import moodbuddy.moodbuddy.global.common.exception.letter.LetterNumsException;
import moodbuddy.moodbuddy.global.common.exception.profile.ProfileImageNotFoundByUserIdException;
import moodbuddy.moodbuddy.global.common.exception.profile.ProfileNotFoundByUserIdException;
import moodbuddy.moodbuddy.global.common.exception.quddyTI.QuddyTINotFoundException;
import moodbuddy.moodbuddy.global.common.exception.user.UserNotFoundByUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MoodBuddyException.class)
    public ResponseEntity<ApiErrorResponse> handleDateRoadException(final MoodBuddyException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ApiErrorResponse.from(e));
    }

//    @ExceptionHandler(UserNotFoundByUserIdException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(UserNotFoundByUserIdException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(new ApiErrorResponse(
//                errorCode.getErrorCode(),
//                ex.getMessage() + " userId : " + ex.getUserId())
//                , HttpStatusCode.valueOf(errorCode.getStatus()));
//    }
//
//    @ExceptionHandler(DiaryTodayExistingException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(DiaryTodayExistingException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        errorCode.getMessage()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(DiaryNotFoundException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(DiaryNotFoundException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        errorCode.getMessage()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//    @ExceptionHandler(DraftDiaryNoAccessException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(DraftDiaryNoAccessException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        errorCode.getMessage()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(BookMarkNotFoundException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(BookMarkNotFoundException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        errorCode.getMessage()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(QuddyTINotFoundException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(QuddyTINotFoundException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        errorCode.getMessage()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(DiaryInsufficientException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(DiaryInsufficientException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        errorCode.getMessage()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//
//    @ExceptionHandler(LetterNotFoundByIdException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(LetterNotFoundByIdException ex){
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        ex.getMessage() + " letterId : " + ex.getLetterId()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(LetterNotFoundByIdAndUserIdException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(LetterNotFoundByIdAndUserIdException ex){
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        ex.getMessage() + " letterId : " + ex.getLetterId() + " userId : " + ex.getUserId()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(LetterNumsException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(LetterNumsException ex){
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        ex.getMessage() + " userId : " + ex.getUserId()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(ProfileImageNotFoundByUserIdException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(ProfileImageNotFoundByUserIdException ex){
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        ex.getMessage() + " userId : " + ex.getUserId()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(ProfileNotFoundByUserIdException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(ProfileNotFoundByUserIdException ex){
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        ex.getMessage() + "  userId : " + ex.getUserId()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
//
//    @ExceptionHandler(ParsingContentException.class)
//    public ResponseEntity<ApiErrorResponse> handleException(ParsingContentException ex){
//        ErrorCode errorCode = ex.getErrorCode();
//        return new ResponseEntity<>(
//                new ApiErrorResponse(
//                        errorCode.getErrorCode(),
//                        ex.getMessage() + " content : " + ex.getContent()),
//                HttpStatus.valueOf(errorCode.getStatus())
//        );
//    }
}


//    사용 예시
//    1. exception class 정의
//    2. api exception handler에 handleException 함수 선언(생성자 오버로딩)
//    3. service layer에서 custom exception 처리
//    4. 실패 case에서 custom exception이 return 되는지 확인해보기
//        @ExceptionHandler(ParameterNullOrEmptyException.class)
//        public ResponseEntity<ApiErrorResponse> handleException(ParameterNullOrEmptyException ex) {
//                return new ResponseEntity<>(
//                new ApiErrorResponse(
//                "JEP-001",
//                ex.getMessage()),
//                HttpStatus.BAD_REQUEST
//                );
//                }
//
//        @ExceptionHandler(DatabaseNullOrEmptyException.class)
//        public ResponseEntity<ApiErrorResponse> handleException(DatabaseNullOrEmptyException ex){
//                return new ResponseEntity<>(
//                new ApiErrorResponse(
//                "JED-001",
//                ex.getMessage()),
//                HttpStatus.INTERNAL_SERVER_ERROR
//                );
//                }


