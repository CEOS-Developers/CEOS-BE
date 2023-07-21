package ceos.backend.domain.project.dto.response;


import ceos.backend.domain.project.domain.Participant;
import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import ceos.backend.domain.project.domain.ProjectUrl;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProjectResponse {

    private Long projectId;
    private String name;
    private String description;
    private int generation;
    private List<ProjectUrl> projectUrls;
    private List<ProjectImage> projectImages;
    private List<Participant> participants;

    @Builder
    public GetProjectResponse(
            Long projectId,
            String name,
            String description,
            int generation,
            List<ProjectUrl> projectUrls,
            List<ProjectImage> projectImages,
            List<Participant> participants) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.generation = generation;
        this.projectUrls = projectUrls;
        this.projectImages = projectImages;
        this.participants = participants;
    }

    public static GetProjectResponse from(Project project) {
        return GetProjectResponse.builder()
                .projectId(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .generation(project.getGeneration())
                .projectUrls(project.getProjectUrls())
                .projectImages(project.getProjectImages())
                .participants(project.getParticipants())
                .build();
    }
}
