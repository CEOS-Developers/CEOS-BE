package ceos.backend.domain.application.exception.exceptions;

import ceos.backend.domain.application.exception.ApplicationErrorCode;
import ceos.backend.global.error.BaseErrorException;

public class NotDeletableDuringRecruitment extends BaseErrorException {
    public static final NotDeletableDuringRecruitment EXCEPTION = new NotDeletableDuringRecruitment();

    private NotDeletableDuringRecruitment() {
        super(ApplicationErrorCode.NOT_DELETABLE_DURING_RECRUITMENT);
    }
}
