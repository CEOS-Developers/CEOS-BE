package ceos.backend.domain.project.exception;


import ceos.backend.global.error.BaseErrorException;

public class ProjectNotFound extends BaseErrorException {

    public static final ProjectNotFound EXCEPTION = new ProjectNotFound();

    private ProjectNotFound() {
        super(ProjectErrorCode.PROJECT_NOT_FOUND);
    }
}
