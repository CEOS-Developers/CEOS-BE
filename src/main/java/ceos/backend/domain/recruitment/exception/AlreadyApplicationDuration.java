package ceos.backend.domain.recruitment.exception;


import ceos.backend.global.error.BaseErrorException;

public class AlreadyApplicationDuration extends BaseErrorException {

    public static final AlreadyApplicationDuration EXCEPTION = new AlreadyApplicationDuration();

    private AlreadyApplicationDuration() {
        super(RecruitmentErrorCode.ALREADY_APPLICATION_DURATION);
    }
}
