package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotDocumentResultCheckDuration extends BaseErrorException {

    public static final NotDocumentResultCheckDuration EXCEPTION = new NotDocumentResultCheckDuration();

    private NotDocumentResultCheckDuration() {
        super(SettingsErrorCode.NOT_DOCUMENT_RESULT_CHECK_DURATION);
    }
}
