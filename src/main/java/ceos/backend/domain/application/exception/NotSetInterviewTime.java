package ceos.backend.domain.application.exception;


import ceos.backend.global.error.BaseErrorException;

public class NotSetInterviewTime extends BaseErrorException {

    public static final NotSetInterviewTime EXCEPTION = new NotSetInterviewTime();

    private NotSetInterviewTime() {
        super(ApplicationErrorCode.NOT_SET_INTERVIEW_TIME);
    }
}
