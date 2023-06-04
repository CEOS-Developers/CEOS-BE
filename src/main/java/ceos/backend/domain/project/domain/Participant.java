package ceos.backend.domain.project.domain;

import ceos.backend.domain.project.vo.ParticipantVo;
import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.Part;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Part part;

    @NotNull
    @Size(max = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 생성자
    @Builder
    private Participant(Part part, String name, Project project) {
        this.part = part;
        this.name = name;
        this.project = project;
    }

    // 정적 팩토리 메서드
    public static Participant of(ParticipantVo participantVo, Project project) {
        return Participant.builder()
                .part(participantVo.getPart())
                .name(participantVo.getName())
                .project(project)
                .build();
    }
}
