package ceos.backend.global.error.exception;

import ceos.backend.global.error.BaseErrorException;
import ceos.backend.global.error.GlobalErrorCode;

public class ExampleGlobalNotFoundException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ExampleGlobalNotFoundException();

    private ExampleGlobalNotFoundException() {
        super(GlobalErrorCode.EXAMPLE_GLOBAL_NOT_FOUND);
    }
}
