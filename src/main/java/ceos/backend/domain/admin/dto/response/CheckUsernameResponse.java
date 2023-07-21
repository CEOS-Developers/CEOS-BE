package ceos.backend.domain.admin.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckUsernameResponse {

    private boolean isAvailable;

    @Builder
    private CheckUsernameResponse(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public static CheckUsernameResponse from(boolean isAvailable) {
        return CheckUsernameResponse.builder().isAvailable(isAvailable).build();
    }
}
