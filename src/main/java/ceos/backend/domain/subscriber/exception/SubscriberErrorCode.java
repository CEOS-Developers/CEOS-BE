package ceos.backend.domain.subscriber.exception;

import static org.springframework.http.HttpStatus.*;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SubscriberErrorCode implements BaseErrorCode {

    /* Data */
    INVALID_ACTION_BEFORE(BAD_REQUEST, "SUBSCRIBER_400_1", "리쿠르팅 시작 전입니다."),
    INVALID_ACTION_AFTER(BAD_REQUEST, "SUBSCRIBER_400_2", "리쿠르팅 마감 후입니다."),
    DUPLICATE_DATA(CONFLICT, "SUBSCRIBER_409_1", "이미 존재하는 데이터입니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
