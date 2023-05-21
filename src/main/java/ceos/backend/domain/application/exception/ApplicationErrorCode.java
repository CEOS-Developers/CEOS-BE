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
    APPLICANT_NOT_FOUND(BAD_REQUEST, "APPLICATION_400_3", "존재하지 않는 지원자입니다."),
    NOT_PASS_DOCUMENT(BAD_REQUEST, "APPLICATION_400_4", "서류 합격 상태가 아닙니다."),
    ALREADY_CHECK_INTERVIEW(BAD_REQUEST, "APPLICATION_400_5", "면접 참여 여부를 이미 선택했습니다."),
    NOT_PASS_FINAL(BAD_REQUEST, "APPLICATION_400_6", "최종 합격 상태가 아닙니다."),
    ALREADY_CHECK_FINAL(BAD_REQUEST, "APPLICATION_400_7", "활동 여부를 이미 선택했습니다."),

    /* Question */
    QUESTION_NOT_FOUND(BAD_REQUEST, "QUESTION_400_1", "존재하지 않는 질문입니다."),

    /* Interview */
    INTERVIEW_NOT_FOUND(BAD_REQUEST, "INTERVIEW_400_1", "존재하지 않는 면접 시간입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);

    }
}
