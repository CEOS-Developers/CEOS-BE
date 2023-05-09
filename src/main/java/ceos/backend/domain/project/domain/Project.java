package ceos.backend.domain.project.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(mappedBy = "project")
    private ProjectImage projectImage;

    // Project : Participant = 1:N (양방향)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    // 연관관계 메서드
    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.setProject(this);
    }
}
