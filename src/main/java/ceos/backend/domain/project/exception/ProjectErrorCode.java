package ceos.backend.domain.project.exception;

import static org.springframework.http.HttpStatus.*;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProjectErrorCode implements BaseErrorCode {
    /* Project */
    DUPLICATE_PROJECT(BAD_REQUEST, "PROJECT_400_1", "이미 존재하는 프로젝트입니다."),
    PROJECT_NOT_FOUND(NOT_FOUND, "PROJECT_404_1", "존재하지 않는 프로젝트입니다."),

    /* Data */
    DUPLICATE_DATA(CONFLICT, "PROJECT_409_1", "이미 존재하는 데이터입니다"),
    DATA_NOT_FOUND(CONFLICT, "PROJECT_404_1", "존재하지 않는 데이터입니다"),
    INVALID_DATA(BAD_REQUEST, "PROJECT_400_2", "데이터 유효성 검증에 실패하였습니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
