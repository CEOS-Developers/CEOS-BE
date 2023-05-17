package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class DuplicateApplicant extends BaseErrorException {

    public static final DuplicateApplicant EXCEPTION = new DuplicateApplicant();

    private DuplicateApplicant() {
        super(ApplicationErrorCode.DUPLICATE_APPLICANT);
    }
}
