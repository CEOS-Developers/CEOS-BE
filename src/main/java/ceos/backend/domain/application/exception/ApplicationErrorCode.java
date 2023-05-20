package ceos.backend.domain.application.exception;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements BaseErrorCode {
    /* Application */
    DUPLICATE_APPLICANT(BAD_REQUEST, "APPLICATION_400_1", "이미 지원한 지원자입니다."),
    WRONG_GENERATION(BAD_REQUEST, "APPLICATION_400_2", "해당 기수를 지원할 수 없습니다."),
    NOT_APPLICATION_DURATION(BAD_REQUEST, "APPLICATION_400_3", "지원 기간이 아닙니다."),

    /* Question */
    QUESTION_NOT_FOUND(BAD_REQUEST, "QUESTION_400_1", "존재하지 않는 질문입니다."),

    /* Interview */
    INTERVIEW_NOT_FOUND(BAD_REQUEST, "INTERVIEW_400_1", "존재하지 않는 면접 시간입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status.value(), code, reason);

    }
}
