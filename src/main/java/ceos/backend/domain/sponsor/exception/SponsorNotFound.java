package ceos.backend.domain.sponsor.exception;

import ceos.backend.global.error.BaseErrorException;

public class SponsorNotFound extends BaseErrorException {

    public static final SponsorNotFound EXCEPTION = new SponsorNotFound();
    private SponsorNotFound() {super(SponsorErrorCode.SPONSOR_NOT_FOUND);}
}
