package ceos.backend.domain.management.exception;


import ceos.backend.global.error.BaseErrorException;

public class ManagerNotFound extends BaseErrorException {

    public static final ManagerNotFound EXCEPTION = new ManagerNotFound();

    private ManagerNotFound() {
        super(ManagementErrorCode.MANAGER_NOT_FOUND);
    }
}
