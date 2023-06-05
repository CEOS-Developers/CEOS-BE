package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class InvalidAction extends BaseErrorException {

    public static final InvalidAction EXCEPTION = new InvalidAction();

    private InvalidAction() {
        super(AdminErrorCode.INVALID_ACTION);
    }
}
