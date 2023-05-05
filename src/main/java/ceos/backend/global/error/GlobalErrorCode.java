package ceos.backend.global.error;

import ceos.backend.global.common.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode{

    EXAMPLE_GLOBAL_NOT_FOUND(NOT_FOUND, "EXAMPLEGLOBAL_404_1", "예시를 찾을 수 없는 오류입니다."),
    ;

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status.value(), code, reason);
    }
}
