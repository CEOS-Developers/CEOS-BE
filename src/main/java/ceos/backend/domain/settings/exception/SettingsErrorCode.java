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
    SETTINGS_NOT_FOUND(BAD_REQUEST, "SETTINGS_400_1", "리크루팅 정보가 설정되어있지 않습니다."),
    NOT_APPLICATION_DURATION(BAD_REQUEST, "SETTINGS_400_2", "지원 기간이 아닙니다."),
    NOT_DOCUMENT_RESULT_CHECK_DURATION(BAD_REQUEST, "SETTINGS_400_3", "서류 결과 확인 기간이 아닙니다."),
    NOT_FINAL_RESULT_CHECK_DURATION(BAD_REQUEST, "SETTINGS_400_4", "최종 결과 확인 기간이 아닙니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);

    }
}
