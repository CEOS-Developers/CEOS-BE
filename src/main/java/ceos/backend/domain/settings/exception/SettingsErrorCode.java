package ceos.backend.domain.settings.exception;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum SettingsErrorCode implements BaseErrorCode {
    SETTINGS_NOT_FOUND(BAD_REQUEST, "INTERVIEW_400_1", "리크루팅 정보가 설정되어있지 않습니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status.value(), code, reason);

    }
}
