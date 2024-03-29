package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class WrongGeneration extends BaseErrorException {

    public static final WrongGeneration EXCEPTION = new WrongGeneration();

    private WrongGeneration() {
        super(ApplicationErrorCode.WRONG_GENERATION);
    }
}
