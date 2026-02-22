package ceos.backend.domain.application.exception.exceptions;

import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class InvalidAvailableCheck extends BaseErrorException {

    public static final InvalidAvailableCheck EXCEPTION = new InvalidAvailableCheck();

    private InvalidAvailableCheck() {
        super(ApplicationErrorCode.INVALID_AVAILABLE_CHECK);
    }
}

