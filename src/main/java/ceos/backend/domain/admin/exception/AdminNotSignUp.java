package ceos.backend.domain.admin.exception;


import ceos.backend.global.error.BaseErrorException;

public class AdminNotSignUp extends BaseErrorException {

    public static final AdminNotSignUp EXCEPTION = new AdminNotSignUp();

    private AdminNotSignUp() {
        super(AdminErrorCode.ADMIN_NOT_SIGN_UP);
    }
}
