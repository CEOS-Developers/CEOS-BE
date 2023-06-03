package ceos.backend.domain.faq.exception;

import ceos.backend.global.error.BaseErrorException;

public class FaqNotFound extends BaseErrorException {

    public static final FaqNotFound EXCEPTION = new FaqNotFound();
    private FaqNotFound() {super(FaqErrorCode.FAQ_NOT_FOUND);}
}
