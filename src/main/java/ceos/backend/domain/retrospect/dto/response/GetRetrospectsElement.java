package ceos.backend.domain.retrospect.dto.response;


import ceos.backend.domain.retrospect.domain.Retrospect;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRetrospectsElement {
    private Long id;
    private String title;
    private Integer generation;
    private String part;

    public static GetRetrospectsElement fromEntity(Retrospect retrospect) {
        return new GetRetrospectsElement(
                retrospect.getId(),
                retrospect.getTitle(),
                retrospect.getGeneration(),
                retrospect.getPart().getPart());
    }
}
