package ceos.backend.global.error;

import ceos.backend.global.common.dto.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getErrorReason();
}
