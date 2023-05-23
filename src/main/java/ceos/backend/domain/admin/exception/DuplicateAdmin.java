package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class DuplicateAdmin extends BaseErrorException {

    public static final DuplicateAdmin EXCEPTION = new DuplicateAdmin();

    private DuplicateAdmin() {
        super(AdminErrorCode.DUPLICATE_ADMIN);
    }
}
