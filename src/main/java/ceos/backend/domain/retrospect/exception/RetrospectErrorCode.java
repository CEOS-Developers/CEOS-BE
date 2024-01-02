package ceos.backend.domain.retrospect.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RetrospectErrorCode implements BaseErrorCode {
    RETROSPECT_NOT_FOUND(BAD_REQUEST, "QUESTION_404_1", "존재하지 않는 질문입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
