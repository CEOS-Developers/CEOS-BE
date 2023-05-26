package ceos.backend.domain.recruitment.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotApplicationDuration extends BaseErrorException {

    public static final NotApplicationDuration EXCEPTION = new NotApplicationDuration();

    private NotApplicationDuration() {
        super(RecruitmentErrorCode.NOT_APPLICATION_DURATION);
    }
}
