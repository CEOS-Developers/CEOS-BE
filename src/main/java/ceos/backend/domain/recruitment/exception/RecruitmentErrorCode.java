package ceos.backend.domain.recruitment.exception;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum RecruitmentErrorCode implements BaseErrorCode {
    RECRUITMENT_NOT_FOUND(BAD_REQUEST, "RECRUITMENT_400_1", "리크루팅 정보가 설정되어있지 않습니다."),
    NOT_APPLICATION_DURATION(BAD_REQUEST, "RECRUITMENT_400_2", "지원 기간이 아닙니다."),
    NOT_DOCUMENT_RESULT_CHECK_DURATION(BAD_REQUEST, "RECRUITMENT_400_3", "서류 결과 확인 기간이 아닙니다."),
    NOT_FINAL_RESULT_CHECK_DURATION(BAD_REQUEST, "RECRUITMENT_400_4", "최종 결과 확인 기간이 아닙니다."),
    NOT_DOCUMENT_PASS_DURATION(BAD_REQUEST, "RECRUITMENT_400_5", "서류 합격 여부 변경 가능 기간이 아닙니다."),
    NOT_FINAL_PASS_DURATION(BAD_REQUEST, "RECRUITMENT_400_6", "최종 합격 여부 변경 가능 기간이 아닙니다."),
    ALREADY_APPLICATION_DURATION(BAD_REQUEST, "RECRUITMENT_400_6", "이미 지원 기간입니다."),
    ;

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);

    }
}
