package ceos.backend.domain.admin.exception;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements BaseErrorCode {
    /* Admin */
    DUPLICATE_ADMIN(BAD_REQUEST, "ADMIN_400_1", "이미 가입한 어드민입니다."),
    INVALID_ACTION(BAD_REQUEST, "ADMIN_400_2", "자기 자신에 대한 작업은 수행할 수 없습니다."),
    NOT_ALLOWED_TO_MODIFY(FORBIDDEN, "ADMIN_403_1", "지원 기간에는 수정할 수 없습니다."),
    ADMIN_NOT_SIGN_UP(NOT_FOUND, "ADMIN_404_1", "회원으로 가입된 유저가 아닙니다"),
    ADMIN_NOT_FOUND(NOT_FOUND, "ADMIN_404_2", "존재하지 않는 어드민입니다."),

    /* Data */
    MISMATCH_NEW_PASSWORD(BAD_REQUEST, "ADMIN_400_3", "새비밀번호가 일치하지 않습니다"),
    MISMATCH_PASSWORD(BAD_REQUEST, "ADMIN_400_4", "비밀번호가 일치하지 않습니다"),
    DUPLICATE_DATA(CONFLICT, "ADMIN_409_1", "이미 존재하는 데이터입니다");
    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);

    }
}
