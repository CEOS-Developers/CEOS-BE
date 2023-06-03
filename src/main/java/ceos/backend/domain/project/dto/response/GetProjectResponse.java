package ceos.backend.domain.project.dto.response;

import ceos.backend.domain.project.domain.Participant;
import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProjectResponse {

    private Long projectId;
    private String name;
    private String description;
    private int generation;
    private String serviceUrl;
    private String behanceUrl;
    private String githubUrl;
    private List<ProjectImage> projectImages;
    private List<Participant> participants;

    @Builder
    public GetProjectResponse(Long projectId,
                              String name,
                              String description,
                              int generation,
                              String serviceUrl,
                              String behanceUrl,
                              String githubUrl,
                              List<ProjectImage> projectImages,
                              List<Participant> participants
    ) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.generation = generation;
        this.serviceUrl = serviceUrl;
        this.behanceUrl = behanceUrl;
        this.githubUrl = githubUrl;
        this.projectImages = projectImages;
        this.participants = participants;
    }

    public static GetProjectResponse from(Project project) {
        return GetProjectResponse.builder()
                .projectId(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .generation(project.getGeneration())
                .serviceUrl(project.getServiceUrl())
                .behanceUrl(project.getBehanceUrl())
                .githubUrl(project.getGithubUrl())
                .projectImages(project.getProjectImages())
                .participants(project.getParticipants())
                .build();
    }
}
