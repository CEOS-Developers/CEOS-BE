package ceos.backend.global.error.exception;


import ceos.backend.global.error.BaseErrorException;
import ceos.backend.global.error.GlobalErrorCode;

public class NoTokenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NoTokenException();

    private NoTokenException() {
        super(GlobalErrorCode.NO_TOKEN);
    }
}
