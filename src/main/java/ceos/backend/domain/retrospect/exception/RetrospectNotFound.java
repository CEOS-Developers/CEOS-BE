package ceos.backend.domain.retrospect.exception;


import ceos.backend.global.error.BaseErrorException;

public class RetrospectNotFound extends BaseErrorException {

    public static final RetrospectNotFound EXCEPTION = new RetrospectNotFound();

    public RetrospectNotFound() {
        super(RetrospectErrorCode.RETROSPECT_NOT_FOUND);
    }
}
