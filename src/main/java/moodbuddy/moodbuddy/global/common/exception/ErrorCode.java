package moodbuddy.moodbuddy.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /** 2XX **/
    OK(200, "COMMON_SUCCESS-200", "Successful"),
    NO_MATCHING_CONTENTS(204, "NO-CONTENT_ERR-204", "NO CONTENT"),

    /** 4XX **/
    EMAIL_DUPLICATION(400,"MEMBER-ERR-400","EMAIL DUPLICATED"),
    BAD_REQUEST_SUMMARY(400, "DIARY-ERR-400", "일기가 요약하기에 불충분합니다."),
    NOT_FOUND(400,"COMMON-ERR-400","PAGE NOT FOUND"),
    NOT_FOUND_DIARY(400, "DIARY-ERR-400", "일기를 찾을 수 없습니다."),
    NOT_FOUND_DIARY_IMAGE(400, "DIARY-IMAGE-ERR-400", "일기 사진을 찾을 수 없습니다."),
    NOT_FOUND_DRAFT_DIARY(400, "DIARY-ERR-400", "임시 저장 일기를 찾을 수 없습니다."),
    NOT_FOUND_BOOK_MARK(400, "BOOK-MARK-ERR-400", "북마크를 찾을 수 없습니다."),
    NOT_FOUND_QUDDYTI(400, "QUDDYTI-ERR-400", "쿼디티아이를 찾을 수 없습니다."),
    NOT_FOUND_USER(400, "USER-ERR-400", "userId에 해당하는 회원을 찾을 수 없습니다."),
    NOT_FOUND_PROFILE(400, "PROFILE-ERR-400", "userId에 해당하는 프로필을 찾을 수 없습니다."),
    NOT_FOUND_PROFILE_IMAGE(400, "PROFILE-IMAGE-ERR-400", "userId에 해당하는 프로필 이미지를 찾을 수 없습니다."),
    LETTER_NOT_FOUND_BY_ID(400, "LETTER-ERR-400","letterId에 해당하는 편지를 찾을 수 없습니다."),
    LETTER_NOT_FOUND_BY_ID_AND_USER_ID(400, "LETTER-ERR-400","letterId와 userId에 해당하는 회원을 찾을 수 없습니다."),
    INVALID_LETTER_NUMS(400, "LETTER-ERR-400","userId에 해당하는 회원의 편지지가 없습니다."),
    NO_ACCESS_DIARY(403, "DIARY-ERR-403", "접근할 수 없는 일기입니다."),
    TODAY_EXISTING_DIARY(409, "DIARY-ERR-409", "오늘 이미 일기를 작성했습니다."),
    GPT_PARSE_ERROR(422, "GPT-ERR-422", "Json 형태의 GPT 응답을 파싱하는 중 오류가 발생했습니다."),

    /** 5XX **/
    INTERNAL_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR");

    private int status;
    private String errorCode;
    private String message;
}

