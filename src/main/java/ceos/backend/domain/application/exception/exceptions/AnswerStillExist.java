package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class AnswerStillExist extends BaseErrorException {

    public static final AnswerStillExist EXCEPTION = new AnswerStillExist();

    private AnswerStillExist() {
        super(ApplicationErrorCode.ANSWERS_STILL_EXIST);
    }
}
