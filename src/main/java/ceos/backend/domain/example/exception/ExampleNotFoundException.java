package ceos.backend.domain.example.exception;

import ceos.backend.global.error.BaseErrorException;

public class ExampleNotFoundException extends BaseErrorException {

    public static final ExampleNotFoundException EXCEPTION = new ExampleNotFoundException();

    private ExampleNotFoundException() {
        super(ExampleErrorCode.EXAMPLE_NOT_FOUND);
    }
}
