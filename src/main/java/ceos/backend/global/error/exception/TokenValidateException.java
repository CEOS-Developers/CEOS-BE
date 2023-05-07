package ceos.backend.global.error.exception;

import ceos.backend.global.error.BaseErrorException;
import ceos.backend.global.error.GlobalErrorCode;

public class TokenValidateException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new TokenValidateException();

    private TokenValidateException() { super(GlobalErrorCode.INVALID_AUTH_TOKEN); }
}
