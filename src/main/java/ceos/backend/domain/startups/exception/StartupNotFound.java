package ceos.backend.domain.startups.exception;


import ceos.backend.global.error.BaseErrorException;

public class StartupNotFound extends BaseErrorException {

    public static final StartupNotFound EXCEPTION = new StartupNotFound();

    public StartupNotFound() {
        super(StartupErrorCode.STARTUP_NOT_FOUND);
    }

}
