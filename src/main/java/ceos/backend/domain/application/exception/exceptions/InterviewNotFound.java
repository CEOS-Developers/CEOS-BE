package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class InterviewNotFound extends BaseErrorException {

    public static final InterviewNotFound EXCEPTION = new InterviewNotFound();

    private InterviewNotFound() {
        super(ApplicationErrorCode.INTERVIEW_NOT_FOUND);
    }
}
