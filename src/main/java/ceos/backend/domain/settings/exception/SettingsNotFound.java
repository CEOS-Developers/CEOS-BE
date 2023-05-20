package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class SettingsNotFound extends BaseErrorException {

    public static final SettingsNotFound EXCEPTION = new SettingsNotFound();

    private SettingsNotFound() {
        super(SettingsErrorCode.SETTINGS_NOT_FOUND);
    }
}
