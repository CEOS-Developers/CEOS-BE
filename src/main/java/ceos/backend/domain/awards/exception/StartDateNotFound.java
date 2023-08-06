package ceos.backend.domain.awards.exception;

import ceos.backend.global.error.BaseErrorException;

public class StartDateNotFound extends BaseErrorException {
    public static final StartDateNotFound EXCEPTION = new StartDateNotFound();

    private StartDateNotFound() {
        super(AwardsErrorCode.START_DATE_NOT_FOUND);
    }
}
