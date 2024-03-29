package ceos.backend.domain.faq.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FaqErrorCode implements BaseErrorCode {
    /* Faq */
    FAQ_NOT_FOUND(BAD_REQUEST, "FAQ_404_1", "해당 FAQ는 존재하지 않습니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
