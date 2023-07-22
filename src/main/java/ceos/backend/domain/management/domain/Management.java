package ceos.backend.domain.management.domain;


import ceos.backend.domain.management.Vo.ManagementVo;
import ceos.backend.domain.management.dto.request.UpdateManagementRequest;
import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.University;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
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
    @Enumerated(EnumType.STRING)
    private ManagementRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ManagementPart part;

    private int generation;

    @NotNull private int managementGeneration;

    @NotNull
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
    private Management(
            String name,
            ManagementRole role,
            ManagementPart part,
            int generation,
            int managementGeneration,
            University university,
            String major,
            String company,
            String imageUrl) {
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
    public static Management from(ManagementVo managementVo) {
        return Management.builder()
                .name(managementVo.getName())
                .role(managementVo.getRole())
                .part(managementVo.getPart())
                .generation(managementVo.getGeneration())
                .managementGeneration(managementVo.getManagementGeneration())
                .university(managementVo.getUniversity())
                .major(managementVo.getMajor())
                .company(managementVo.getCompany())
                .imageUrl(managementVo.getImageUrl())
                .build();
    }

    public void update(UpdateManagementRequest request) {
        ManagementVo managementInfo = request.getManagementVo();
        if (managementInfo.getName() != null) {
            this.name = managementInfo.getName();
        }
        if (managementInfo.getRole() != null) {
            this.role = managementInfo.getRole();
        }
        if (managementInfo.getPart() != null) {
            this.part = managementInfo.getPart();
        }
        if (Optional.ofNullable(managementInfo.getGeneration()).orElse(0) != 0) {
            this.generation = managementInfo.getGeneration();
        }
        if (Optional.ofNullable(managementInfo.getManagementGeneration()).orElse(0) != 0) {
            this.managementGeneration = managementInfo.getManagementGeneration();
        }
        if (managementInfo.getUniversity() != null) {
            this.university = managementInfo.getUniversity();
        }
        if (managementInfo.getMajor() != null) {
            this.major = managementInfo.getMajor();
        }
        if (managementInfo.getCompany() != null) {
            this.company = managementInfo.getCompany();
        }
        if (managementInfo.getImageUrl() != null) {
            this.imageUrl = managementInfo.getImageUrl();
        }
    }
}
