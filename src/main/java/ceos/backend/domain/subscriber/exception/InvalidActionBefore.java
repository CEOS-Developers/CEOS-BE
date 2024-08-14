package ceos.backend.domain.subscriber.exception;


import ceos.backend.global.error.BaseErrorException;

public class InvalidActionBefore extends BaseErrorException {

    public static final InvalidActionBefore EXCEPTION = new InvalidActionBefore();

    private InvalidActionBefore() {
        super(SubscriberErrorCode.INVALID_ACTION_BEFORE);
    }
}
