package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotApplicationDuration extends BaseErrorException {

    public static final NotApplicationDuration EXCEPTION = new NotApplicationDuration();

    private NotApplicationDuration() {
        super(SettingsErrorCode.NOT_APPLICATION_DURATION);
    }
}
