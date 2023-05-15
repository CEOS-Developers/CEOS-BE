package ceos.backend.domain.management.domain;

import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.University;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Management extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "management_id")
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private ManagementRole role;

    @NotNull
    @Size(max = 20)
    private String part;

    private int generation;

    @NotNull
    private int managementGeneration;

    @NotNull
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private University university;

    @NotNull
    @Size(max = 20)
    private String major;

    @NotNull
    @Size(max = 50)
    private String company;

    private String imageUrl;

    // 생성자
    @Builder
    private Management(String name,
                       ManagementRole role,
                       String part,
                       int generation,
                       int managementGeneration,
                       University university,
                       String major,
                       String company,
                       String imageUrl)
    {
        this.name = name;
        this.role = role;
        this.part = part;
        this.generation = generation;
        this.managementGeneration = managementGeneration;
        this.university = university;
        this.major = major;
        this.company = company;
        this.imageUrl = imageUrl;
    }

    // 정적 팩토리 메서드
}