package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotMatchingQnA extends BaseErrorException {

    public static final NotMatchingQnA EXCEPTION = new NotMatchingQnA();

    private NotMatchingQnA() {
        super(ApplicationErrorCode.NOT_MATCHING_QNA);
    }
}
