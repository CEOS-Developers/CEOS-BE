package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class ApplicationInterviewStillExist extends BaseErrorException {

    public static final ApplicationInterviewStillExist EXCEPTION =
            new ApplicationInterviewStillExist();

    private ApplicationInterviewStillExist() {
        super(ApplicationErrorCode.APPLICATION_INTERVIEW_STILL_EXIST);
    }
}
