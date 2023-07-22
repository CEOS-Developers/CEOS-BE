package ceos.backend.domain.application.exception;


import ceos.backend.global.error.BaseErrorException;

public class QuestionNotFound extends BaseErrorException {

    public static final QuestionNotFound EXCEPTION = new QuestionNotFound();

    private QuestionNotFound() {
        super(ApplicationErrorCode.QUESTION_NOT_FOUND);
    }
}
