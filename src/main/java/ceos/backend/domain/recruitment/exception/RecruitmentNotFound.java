package ceos.backend.domain.recruitment.exception;

import ceos.backend.global.error.BaseErrorException;

public class RecruitmentNotFound extends BaseErrorException {

    public static final RecruitmentNotFound EXCEPTION = new RecruitmentNotFound();

    private RecruitmentNotFound() {
        super(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND);
    }
}
