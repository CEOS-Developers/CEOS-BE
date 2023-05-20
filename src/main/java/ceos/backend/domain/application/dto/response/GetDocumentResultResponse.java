package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetDocumentResultResponse {
    private Pass pass;

    @Builder
    private GetDocumentResultResponse(Pass pass) {
        this.pass = pass;
    }

    public static GetDocumentResultResponse from(Application application) {
        return GetDocumentResultResponse.builder()
                .pass(application.getDocumentPass())
                .build();
    }
}
