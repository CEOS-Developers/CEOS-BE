package ceos.backend.domain.admin.exception;


import ceos.backend.global.error.BaseErrorException;

public class MismatchNewPassword extends BaseErrorException {

    public static final MismatchNewPassword EXCEPTION = new MismatchNewPassword();

    private MismatchNewPassword() {
        super(AdminErrorCode.MISMATCH_NEW_PASSWORD);
    }
}
