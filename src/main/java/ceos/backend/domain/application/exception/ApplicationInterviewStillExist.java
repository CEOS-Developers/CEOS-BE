package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class ApplicationInterviewStillExist extends BaseErrorException {

    public static final ApplicationInterviewStillExist EXCEPTION = new ApplicationInterviewStillExist();

    private ApplicationInterviewStillExist() {
        super(ApplicationErrorCode.APPLICATION_INTERVIEW_STILL_EXIST);
    }
}
