package ceos.backend.domain.application.exception;


import ceos.backend.global.error.BaseErrorException;

public class InterviewNotFound extends BaseErrorException {

    public static final InterviewNotFound EXCEPTION = new InterviewNotFound();

    private InterviewNotFound() {
        super(ApplicationErrorCode.INTERVIEW_NOT_FOUND);
    }
}
