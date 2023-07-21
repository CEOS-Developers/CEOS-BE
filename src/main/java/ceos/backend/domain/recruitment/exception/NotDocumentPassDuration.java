package ceos.backend.domain.recruitment.exception;


import ceos.backend.global.error.BaseErrorException;

public class NotDocumentPassDuration extends BaseErrorException {

    public static final NotDocumentPassDuration EXCEPTION = new NotDocumentPassDuration();

    private NotDocumentPassDuration() {
        super(RecruitmentErrorCode.NOT_DOCUMENT_PASS_DURATION);
    }
}
