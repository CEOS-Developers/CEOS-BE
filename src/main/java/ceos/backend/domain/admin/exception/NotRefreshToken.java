package ceos.backend.domain.admin.exception;

import ceos.backend.global.error.BaseErrorException;

public class NotRefreshToken extends BaseErrorException {

    public static final NotRefreshToken EXCEPTION = new NotRefreshToken();

    public NotRefreshToken() {
        super(AdminErrorCode.NOT_REFRESH_TOKEN);
    }
}
