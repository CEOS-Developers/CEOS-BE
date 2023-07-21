package ceos.backend.domain.activity.exception;


import ceos.backend.global.error.BaseErrorException;

public class ActivityNotFound extends BaseErrorException {

    public static final ActivityNotFound EXCEPTION = new ActivityNotFound();

    public ActivityNotFound() {
        super(ActivityErrorCode.ACTIVITY_NOT_FOUND);
    }
}
