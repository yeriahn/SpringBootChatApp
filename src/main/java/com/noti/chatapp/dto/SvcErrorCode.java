package com.noti.chatapp.dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SvcErrorCode {
    /* 1xxx */
    /* 2xxx */
    RT_SUCCESS("2001", "성공"),
    /* 3xxx */
    /* 4xxx */
    INVALID_REQUEST("4001", "Invalid request"),
    REQUEST_FORMAT_ERROR("4002", "Request format error"),
    MISSING_HEAD_VALUE("4003", "Request missing header"),
    MISSING_BODY_VALUE("4004", "Request missing body"),
    INVALID_HEAD_VALUE("4005", "Request header value error"),
    INVALID_BODY_VALUE("4006", "Request body value error"),
    TOO_MANY_REQUEST("4007", "Too many request"),
    SERIALIZE_ERROR("4008", "Serialization fail"),
    /* 5xxx */
    NOTFOUND_ROOMID("5001", "채팅방이 존재하지 않습니다."),
    NOTFOUND_USER("5001", "등록된 아이디가 없습니다. 다시 한번 확인 하여주세요."),
    DUPLICATED_ID_CREATE("5007", "중복된 ID 생성 시도"),
    /* 6xxx */
    REDIS_SUBSCRIBER_FAIL("6001", "Redis subscriber 없음"),
    REDIS_CONNECT_FAIL("6002", "Redis connect error"),
    REDIS_SET_FAIL("6003", "Redis set error"),

    /* 8xxx */
    DB_CONNECT_ERROR("8001", "DB connect error"),
    DB_UPDATE_ERROR("8002", "DB update error"),
    DB_SQL_ERROR("8003", "DB query error"),
    DB_DATA_NOTMATCH("8004", "DB data 불일치"),
    DB_DUPLICATE_KEY("8005", "중복(key)된 데이터"),
    DB_NODATA_FOUND("8006", "데이터가 없습니다."),
    /* 9xxx */
    UNEXPECTED_ERROR("9001", "Unexpected error"),
    RT_FAIL("9999", "기타 실패");

    private final String code;
    private final String reasonPhrase;

    SvcErrorCode(String code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public String getCode() {
        return this.code;
    }
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public SvcErrorCode valueOf(int code) {
        String strCode = String.format("%04d", code);
        SvcErrorCode status = resolve(strCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + strCode + "]");
        }
        return status;
    }

    public SvcErrorCode resolve(String code) {
        for (SvcErrorCode status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}
