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
    NOT_FOUND_QUDDYTI(400, "QUDDYTI-ERR-400", "쿼디티아이를 찾을 수 없습니다."),
    NOT_FOUND_USER(400, "USER-ERR-400", "아이디에 해당하는 회원을 찾을 수 없습니다."),
    NO_ACCESS_DIARY(403, "DIARY-ERR-403", "접근할 수 없는 일기입니다."),
    TODAY_EXISTING_DIARY(409, "DIARY-ERR-409", "오늘 이미 일기를 작성했습니다."),

    /** 5XX **/
    INTERNAL_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR");

    private int status;
    private String errorCode;
    private String message;
}

