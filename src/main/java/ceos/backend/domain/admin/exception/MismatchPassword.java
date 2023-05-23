package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class MismatchPassword extends BaseErrorException {

    public static final MismatchPassword EXCEPTION = new MismatchPassword();

    private MismatchPassword() {
        super(AdminErrorCode.MISMATCH_PASSWORD);
    }
}

