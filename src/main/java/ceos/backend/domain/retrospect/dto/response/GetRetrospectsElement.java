package ceos.backend.domain.retrospect.dto.response;


import ceos.backend.domain.retrospect.domain.Retrospect;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRetrospectsElement {
    private Long id;
    private String title;
    private String url;
    private String writer;
    private Integer generation;

    public static GetRetrospectsElement fromEntity(Retrospect retrospect) {
        return new GetRetrospectsElement(
                retrospect.getId(),
                retrospect.getTitle(),
                retrospect.getUrl(),
                retrospect.getWriter(),
                retrospect.getGeneration());
    }
}
