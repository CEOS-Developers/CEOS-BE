package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotApplicationDuration extends BaseErrorException {

    public static final NotApplicationDuration EXCEPTION = new NotApplicationDuration();

    private NotApplicationDuration() {
        super(ApplicationErrorCode.NOT_APPLICATION_DURATION);
    }
}
