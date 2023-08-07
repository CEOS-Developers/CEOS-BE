package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class NotPassFinal extends BaseErrorException {

    public static final NotPassFinal EXCEPTION = new NotPassFinal();

    private NotPassFinal() {
        super(ApplicationErrorCode.NOT_PASS_FINAL);
    }
}
