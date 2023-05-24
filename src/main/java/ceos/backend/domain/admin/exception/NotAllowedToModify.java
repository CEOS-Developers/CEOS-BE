package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotAllowedToModify extends BaseErrorException {

    public static final NotAllowedToModify EXCEPTION = new NotAllowedToModify();

    public NotAllowedToModify() {
        super(AdminErrorCode.NOT_ALLOWED_TO_MODIFY);
    }
}
