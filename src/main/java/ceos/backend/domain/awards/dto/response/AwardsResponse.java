package ceos.backend.domain.awards.dto.response;


import ceos.backend.domain.awards.domain.Awards;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwardsResponse {

    private Long id;
    private String content;

    @Builder
    private AwardsResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static AwardsResponse to(Awards awards) {
        return AwardsResponse.builder().id(awards.getId()).content(awards.getContent()).build();
    }
}
