package ceos.backend.domain.awards.exception;

import ceos.backend.global.error.BaseErrorException;

public class DuplicateGeneration extends BaseErrorException {
    public static final DuplicateGeneration EXCEPTION = new DuplicateGeneration();

    private DuplicateGeneration() {
        super(AwardsErrorCode.DUPLICATE_GENERATION);
    }
}
