package ceos.backend.domain.recruitment.exception;


import ceos.backend.global.error.BaseErrorException;

public class NotFinalResultCheckDuration extends BaseErrorException {

    public static final NotFinalResultCheckDuration EXCEPTION = new NotFinalResultCheckDuration();

    private NotFinalResultCheckDuration() {
        super(RecruitmentErrorCode.NOT_FINAL_RESULT_CHECK_DURATION);
    }
}
