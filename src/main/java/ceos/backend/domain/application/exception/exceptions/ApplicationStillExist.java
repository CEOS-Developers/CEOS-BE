package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class ApplicationStillExist extends BaseErrorException {

    public static final ApplicationStillExist EXCEPTION = new ApplicationStillExist();

    private ApplicationStillExist() {
        super(ApplicationErrorCode.ALREADY_CHECK_INTERVIEW);
    }
}
