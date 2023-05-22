package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class InvalidPassword extends BaseErrorException {

    public static final InvalidPassword EXCEPTION = new InvalidPassword();

    private InvalidPassword() {
        super(AdminErrorCode.INVALID_PASSWORD);
    }
}

