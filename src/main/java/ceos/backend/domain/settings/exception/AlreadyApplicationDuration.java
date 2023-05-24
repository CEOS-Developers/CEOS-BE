package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class AlreadyApplicationDuration extends BaseErrorException {

    public static final AlreadyApplicationDuration EXCEPTION = new AlreadyApplicationDuration();

    private AlreadyApplicationDuration() {
        super(SettingsErrorCode.ALREADY_APPLICATION_DURATION);
    }
}
