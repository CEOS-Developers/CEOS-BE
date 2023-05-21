package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotFinalResultCheckDuration extends BaseErrorException {

    public static final NotFinalResultCheckDuration EXCEPTION = new NotFinalResultCheckDuration();

    private NotFinalResultCheckDuration() {
        super(SettingsErrorCode.NOT_FINAL_RESULT_CHECK_DURATION);
    }
}
