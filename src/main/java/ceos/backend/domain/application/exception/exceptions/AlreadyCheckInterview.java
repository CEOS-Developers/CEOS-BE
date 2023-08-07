package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class AlreadyCheckInterview extends BaseErrorException {

    public static final AlreadyCheckInterview EXCEPTION = new AlreadyCheckInterview();

    private AlreadyCheckInterview() {
        super(ApplicationErrorCode.ALREADY_CHECK_INTERVIEW);
    }
}
