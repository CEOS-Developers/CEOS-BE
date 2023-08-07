package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class AlreadyCheckFinal extends BaseErrorException {

    public static final AlreadyCheckFinal EXCEPTION = new AlreadyCheckFinal();

    private AlreadyCheckFinal() {
        super(ApplicationErrorCode.ALREADY_CHECK_FINAL);
    }
}
