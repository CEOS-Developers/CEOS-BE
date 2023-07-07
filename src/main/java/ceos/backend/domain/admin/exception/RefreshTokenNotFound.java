package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class RefreshTokenNotFound extends BaseErrorException {

    public static final RefreshTokenNotFound EXCEPTION = new RefreshTokenNotFound();

    private RefreshTokenNotFound() {
        super(AdminErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
