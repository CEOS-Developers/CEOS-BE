package ceos.backend.domain.project.domain;

import ceos.backend.domain.project.vo.ProjectInfoVo;
import ceos.backend.global.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 100)
    private String description;

    @NotNull
    private int generation;

    // Project : ProjectUrl = 1:N
    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<ProjectUrl> projectUrls;

    // Project : ProjectImage = 1:N
    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<ProjectImage> projectImages;

    // Project : Participant = 1:N
    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Participant> participants;

    // 생성자
    @Builder
    private Project(String name,
                    String description,
                    int generation) {
        this.name = name;
        this.description = description;
        this.generation = generation;
    }

    public static Project from(ProjectInfoVo projectInfoVo) {
        return Project.builder()
                .name(projectInfoVo.getName())
                .description(projectInfoVo.getDescription())
                .generation(projectInfoVo.getGeneration())
                .build();
    }

    public void update(ProjectInfoVo projectInfoVo) {
        this.name = projectInfoVo.getName();
        this.description = projectInfoVo.getDescription();
        this.generation = projectInfoVo.getGeneration();
    }
}
