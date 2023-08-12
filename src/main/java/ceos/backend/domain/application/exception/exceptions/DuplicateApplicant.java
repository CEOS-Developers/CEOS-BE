package ceos.backend.domain.application.exception.exceptions;


import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class DuplicateApplicant extends BaseErrorException {

    public static final DuplicateApplicant EXCEPTION = new DuplicateApplicant();

    private DuplicateApplicant() {
        super(ApplicationErrorCode.DUPLICATE_APPLICANT);
    }
}
