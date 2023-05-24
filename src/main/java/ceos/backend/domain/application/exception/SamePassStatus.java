package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class SamePassStatus extends BaseErrorException {

    public static final SamePassStatus EXCEPTION = new SamePassStatus();

    private SamePassStatus() {
        super(ApplicationErrorCode.SAME_PASS_STATUS);
    }
}
