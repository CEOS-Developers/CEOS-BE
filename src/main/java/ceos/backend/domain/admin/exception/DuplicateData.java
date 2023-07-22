package ceos.backend.domain.admin.exception;


import ceos.backend.global.error.BaseErrorException;

public class DuplicateData extends BaseErrorException {

    public static final DuplicateData EXCEPTION = new DuplicateData();

    private DuplicateData() {
        super(AdminErrorCode.DUPLICATE_DATA);
    }
}
