package ceos.backend.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindIdResponse {

    private String username;

    @Builder
    private FindIdResponse(String username){
        this.username = username;
    }

    public static FindIdResponse from(String username){
        return FindIdResponse.builder()
                .username(username)
                .build();
    }
}
