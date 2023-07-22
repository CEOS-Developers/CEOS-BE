package ceos.backend.domain.management.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ManagementErrorCode implements BaseErrorCode {
    /* Management */
    MANAGER_NOT_FOUND(BAD_REQUEST, "MANAGEMENT_404_1", "해당 임원진이 존재하지 않습니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
