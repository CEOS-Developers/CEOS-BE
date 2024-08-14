package ceos.backend.domain.subscriber.exception;


import ceos.backend.global.error.BaseErrorException;

public class InvalidActionAfter extends BaseErrorException {

    public static final InvalidActionAfter EXCEPTION = new InvalidActionAfter();

    private InvalidActionAfter() {
        super(SubscriberErrorCode.INVALID_ACTION_AFTER);
    }
}
