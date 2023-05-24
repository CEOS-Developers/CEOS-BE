package ceos.backend.domain.settings.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotDocumentPassDuration extends BaseErrorException {

    public static final NotDocumentPassDuration EXCEPTION = new NotDocumentPassDuration();

    private NotDocumentPassDuration() {
        super(SettingsErrorCode.NOT_DOCUMENT_PASS_DURATION);
    }
}
