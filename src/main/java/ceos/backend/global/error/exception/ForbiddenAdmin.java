package ceos.backend.global.error.exception;


import ceos.backend.global.error.BaseErrorException;
import ceos.backend.global.error.GlobalErrorCode;

public class ForbiddenAdmin extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ForbiddenAdmin();

    private ForbiddenAdmin() {
        super(GlobalErrorCode.FORBIDDEN_ADMIN);
    }
}
