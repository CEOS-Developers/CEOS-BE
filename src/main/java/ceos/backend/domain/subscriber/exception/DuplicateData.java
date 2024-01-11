package ceos.backend.domain.subscriber.exception;


import ceos.backend.global.error.BaseErrorException;

public class DuplicateData extends BaseErrorException {

    public static final DuplicateData EXCEPTION = new DuplicateData();

    private DuplicateData() {
        super(SubscriberErrorCode.DUPLICATE_DATA);
    }
}
