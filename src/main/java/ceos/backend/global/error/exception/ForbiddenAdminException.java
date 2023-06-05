package ceos.backend.global.error.exception;

import ceos.backend.global.error.BaseErrorException;
import ceos.backend.global.error.GlobalErrorCode;

public class ForbiddenAdminException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ForbiddenAdminException();

    private ForbiddenAdminException() { super(GlobalErrorCode.FORBIDDEN_ADMIN); }
}
