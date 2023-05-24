package ceos.backend.domain.management.dto;

import ceos.backend.domain.management.Vo.ManagementVo;
import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.domain.ManagementRole;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.University;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ManagementDto {

    private String name;
    private ManagementRole role;
    private String part;
    private int generation;
    private int managementGeneration;
    private University university;
    private String major;
    private String company;
    private String imageUrl;

    @Builder
    private ManagementDto(String name, ManagementRole role, String part, int generation, int managementGeneration, University university, String major, String company, String imageUrl) {
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

    public static ManagementDto voToDto(ManagementVo managementVo) {
        return ManagementDto.builder()
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

    public static ManagementDto entityToDto(Management management) {
        return ManagementDto.builder()
                .name(management.getName())
                .role(management.getRole())
                .part(management.getPart())
                .generation(management.getGeneration())
                .managementGeneration(management.getManagementGeneration())
                .university(management.getUniversity())
                .major(management.getMajor())
                .company(management.getCompany())
                .imageUrl(management.getImageUrl())
                .build();
    }
}
