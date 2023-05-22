package ceos.backend.domain.admin.exception;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements BaseErrorCode {
    /* Admin */
    DUPLICATE_ADMIN(BAD_REQUEST, "ADMIN_400_1", "이미 가입한 어드민입니다."),
    ADMIN_NOT_FOUND(BAD_REQUEST, "ADMIN_400_2", "존재하지 않는 어드민입니다."),
    ADMIN_NOT_SIGN_UP(NOT_FOUND, "ADMIN_404_1", "회원으로 가입된 유저가 아닙니다"),

    /* Data */
    INVALID_PASSWORD(BAD_REQUEST, "ADMIN_400_1", "비밀번호가 일치하지 않습니다");
    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);

    }
}
