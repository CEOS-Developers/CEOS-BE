package ceos.backend.domain.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCreationTime {
    private String createAt;

    @Builder
    private GetCreationTime(String createAt) {
        this.createAt = createAt;
    }

    public static GetCreationTime from(String createAt) {
        return GetCreationTime.builder()
                .createAt(createAt)
                .build();
    }
}
