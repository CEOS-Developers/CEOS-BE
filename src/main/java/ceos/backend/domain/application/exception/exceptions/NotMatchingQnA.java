package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class NotMatchingQnA extends BaseErrorException {

    public static final NotMatchingQnA EXCEPTION = new NotMatchingQnA();

    private NotMatchingQnA() {
        super(ApplicationErrorCode.NOT_MATCHING_QNA);
    }
}
