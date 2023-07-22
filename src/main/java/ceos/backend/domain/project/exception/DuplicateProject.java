package ceos.backend.domain.project.exception;


import ceos.backend.global.error.BaseErrorException;

public class DuplicateProject extends BaseErrorException {

    public static final DuplicateProject EXCEPTION = new DuplicateProject();

    private DuplicateProject() {
        super(ProjectErrorCode.DUPLICATE_PROJECT);
    }
}
