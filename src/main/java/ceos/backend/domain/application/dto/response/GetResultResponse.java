package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetResultResponse {
    private Pass pass;

    @Builder
    private GetResultResponse(Pass pass) {
        this.pass = pass;
    }

    public static GetResultResponse toDocumentResult(Application application) {
        return GetResultResponse.builder()
                .pass(application.getDocumentPass())
                .build();
    }

    public static GetResultResponse toFinalResult(Application application) {
        return GetResultResponse.builder()
                .pass(application.getFinalPass())
                .build();
    }
}
