package ceos.backend.domain.project.exception;

import ceos.backend.global.error.BaseErrorException;

public class InvalidData extends BaseErrorException {

    public static final InvalidData EXCEPTION = new InvalidData();

    private InvalidData() {
        super(ProjectErrorCode.INVALID_DATA);
    }
}
