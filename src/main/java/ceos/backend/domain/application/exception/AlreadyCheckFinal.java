package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class AlreadyCheckFinal extends BaseErrorException {

    public static final AlreadyCheckFinal EXCEPTION = new AlreadyCheckFinal();

    private AlreadyCheckFinal() {
        super(ApplicationErrorCode.ALREADY_CHECK_FINAL);
    }
}
