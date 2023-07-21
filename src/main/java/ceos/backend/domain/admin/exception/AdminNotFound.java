package ceos.backend.domain.admin.exception;


import ceos.backend.global.error.BaseErrorException;

public class AdminNotFound extends BaseErrorException {

    public static final AdminNotFound EXCEPTION = new AdminNotFound();

    private AdminNotFound() {
        super(AdminErrorCode.ADMIN_NOT_FOUND);
    }
}
