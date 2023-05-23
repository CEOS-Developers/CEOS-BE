package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotFinalPassDuration extends BaseErrorException {

    public static final NotFinalPassDuration EXCEPTION = new NotFinalPassDuration();

    private NotFinalPassDuration() {
        super(SettingsErrorCode.NOT_FINAL_PASS_DURATION);
    }
}
