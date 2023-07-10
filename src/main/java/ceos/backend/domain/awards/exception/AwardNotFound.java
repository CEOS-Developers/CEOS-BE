package ceos.backend.domain.awards.exception;

import ceos.backend.global.error.BaseErrorException;

public class AwardNotFound extends BaseErrorException {
    public static final AwardNotFound EXCEPTION = new AwardNotFound();
    private AwardNotFound() {
        super(AwardsErrorCode.AWARD_NOT_FOUND);
    }
}
