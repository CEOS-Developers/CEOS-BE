package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class NotPassDocument extends BaseErrorException {

    public static final NotPassDocument EXCEPTION = new NotPassDocument();

    private NotPassDocument() {
        super(ApplicationErrorCode.NOT_PASS_DOCUMENT);
    }
}
