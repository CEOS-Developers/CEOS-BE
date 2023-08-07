package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class SamePassStatus extends BaseErrorException {

    public static final SamePassStatus EXCEPTION = new SamePassStatus();

    private SamePassStatus() {
        super(ApplicationErrorCode.SAME_PASS_STATUS);
    }
}
