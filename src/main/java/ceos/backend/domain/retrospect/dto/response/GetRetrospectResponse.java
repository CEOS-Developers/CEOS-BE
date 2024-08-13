package ceos.backend.domain.retrospect.dto.response;


import ceos.backend.domain.retrospect.domain.Retrospect;
import lombok.Data;

@Data
public class GetRetrospectResponse {
    private Long id;
    private String title;
    private String url;
    private String writer;
    private Integer generation;
    private String part;

    public static GetRetrospectResponse fromEntity(Retrospect retrospect) {
        GetRetrospectResponse getRetrospectResponse = new GetRetrospectResponse();

        getRetrospectResponse.setId(retrospect.getId());
        getRetrospectResponse.setTitle(retrospect.getTitle());
        getRetrospectResponse.setUrl(retrospect.getUrl());
        getRetrospectResponse.setWriter(retrospect.getWriter());
        getRetrospectResponse.setGeneration(retrospect.getGeneration());
        getRetrospectResponse.setPart(retrospect.getPart().getPart());

        return getRetrospectResponse;
    }
}
