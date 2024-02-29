package ceos.backend.domain.subscriber.exception;


import ceos.backend.global.error.BaseErrorException;

public class InvalidAction extends BaseErrorException {

    public static final InvalidAction EXCEPTION = new InvalidAction();

    private InvalidAction() {
        super(SubscriberErrorCode.INVALID_ACTION);
    }
}
