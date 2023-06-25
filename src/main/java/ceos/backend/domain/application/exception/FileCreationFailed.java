package ceos.backend.domain.application.exception;

import ceos.backend.global.error.BaseErrorException;

public class FileCreationFailed extends BaseErrorException {

    public static final FileCreationFailed EXCEPTION = new FileCreationFailed();

    private FileCreationFailed() {
        super(ApplicationErrorCode.FILE_CREATION_FAILED);
    }
}

