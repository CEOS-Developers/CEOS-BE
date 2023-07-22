package ceos.backend.domain.project.exception;


import ceos.backend.global.error.BaseErrorException;

public class DataNotFound extends BaseErrorException {

    public static final DataNotFound EXCEPTION = new DataNotFound();

    private DataNotFound() {
        super(ProjectErrorCode.DATA_NOT_FOUND);
    }
}
