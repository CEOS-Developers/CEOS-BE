package ceos.backend.domain.sponsor.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SponsorErrorCode implements BaseErrorCode {
    /* Sponsor */
    SPONSOR_NOT_FOUND(BAD_REQUEST, "SPONSOR_404_1", "해당 후원사는 존재하지 않습니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
