package ceos.backend.domain.application.exception;


import ceos.backend.global.error.BaseErrorException;

public class AnswerStillExist extends BaseErrorException {

    public static final AnswerStillExist EXCEPTION = new AnswerStillExist();

    private AnswerStillExist() {
        super(ApplicationErrorCode.ANSWERS_STILL_EXIST);
    }
}
