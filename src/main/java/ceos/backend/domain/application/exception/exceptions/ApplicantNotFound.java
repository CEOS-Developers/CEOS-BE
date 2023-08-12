package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class ApplicantNotFound extends BaseErrorException {

    public static final ApplicantNotFound EXCEPTION = new ApplicantNotFound();

    private ApplicantNotFound() {
        super(ApplicationErrorCode.APPLICANT_NOT_FOUND);
    }
}
