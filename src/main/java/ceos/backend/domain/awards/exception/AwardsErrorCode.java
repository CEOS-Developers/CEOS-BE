package ceos.backend.domain.awards.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AwardsErrorCode implements BaseErrorCode {
    AWARD_NOT_FOUND(BAD_REQUEST, "AWARD_404_1", "해당 수상이력이 존재하지 않습니다"),
    DUPLICATE_GENERATION(CONFLICT, "AWARD_409_1", "해당 기수의 데이터가 이미 존재합니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
