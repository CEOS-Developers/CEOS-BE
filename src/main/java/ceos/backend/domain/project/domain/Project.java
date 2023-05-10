package ceos.backend.domain.project.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
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

    private String serviceUrl;

    private String instagramUrl;

    private String behanceUrl;

    private String githubUrl;

    // Project : ProjectImage = 1:1 (단방향)
    @OneToOne
    @JoinColumn(name = "project_image_id")
    private ProjectImage projectImage;

    // Project : Participant = 1:N (단방향)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    // 연관관계 메서드
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    // 생성자
    @Builder
    private Project(String name,
                    String description,
                    int generation,
                    String serviceUrl,
                    String instagramUrl,
                    String behanceUrl,
                    String githubUrl,
                    ProjectImage projectImage,
                    List<Participant> participants) {
        this.name = name;
        this.description = description;
        this.generation = generation;
        this.serviceUrl = serviceUrl;
        this.instagramUrl = instagramUrl;
        this.behanceUrl = behanceUrl;
        this.githubUrl = githubUrl;
        this.projectImage = projectImage;
        this.participants = participants;
    }

    // 정적 팩토리 메서드
}
