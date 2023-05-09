package ceos.backend.domain.management.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private String role; // enum

    @NotNull
    @Size(max = 20)
    private String part;

    private int generation;

    @NotNull
    private int managementGeneration;

    @NotNull
    @Size(max = 10)
    private String university; // enum

    @NotNull
    @Size(max = 20)
    private String major;

    @NotNull
    @Size(max = 50)
    private String company;

    private String imageUrl;
}
