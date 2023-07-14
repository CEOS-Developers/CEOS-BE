package ceos.backend.domain.management.mapper;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.domain.ManagementPart;
import ceos.backend.domain.management.dto.ManagementDto;
import ceos.backend.domain.management.dto.response.GetAllManagementsResponse;
import ceos.backend.domain.management.Vo.ManagementVo;
import ceos.backend.domain.management.dto.response.GetAllPartManagementsResponse;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManagementMapper {
    public Management toEntity(ManagementVo managementVo) {
        return Management.from(managementVo);
    }

    public GetAllManagementsResponse toManagementsPage(List<Management> managements, PageInfo pageInfo) {
        List<ManagementDto> managementDtoList = new ArrayList<>();
        for (Management m : managements) {
            ManagementDto managementDto = ManagementDto.entityToDto(m);
            managementDtoList.add(managementDto);
        }
        return GetAllManagementsResponse.of(managementDtoList, pageInfo);
    }

    public GetAllPartManagementsResponse toPartManagementList(List<Management> presidency, List<Management> generalAffairs, List<Management> partLeaders, List<Management> managements) {
        List<ManagementDto> presidencyList = toManagementDtoList(toOrderByPart(presidency));
        List<ManagementDto> generalAffairsList = toManagementDtoList(toOrderByPart(generalAffairs));
        List<ManagementDto> partLeadersList = toManagementDtoList(toOrderByPart(partLeaders));
        List<ManagementDto> managementsList = toManagementDtoList(toOrderByPart(managements));
        return GetAllPartManagementsResponse.of(presidencyList, generalAffairsList, partLeadersList, managementsList);
    }

    public List<ManagementDto> toManagementDtoList(List<Management> managements) {
        List<ManagementDto> managementsDtoList = new ArrayList<>();
        for (Management m : managements) {
            ManagementDto managementDto = ManagementDto.entityToDto(m);
            managementsDtoList.add(managementDto);
        }
        return managementsDtoList;
    }

    public List<Management> toOrderByPart(List<Management> managements) {
        List<Management> orderedManagementList = new ArrayList<>();
        for (ManagementPart p : ManagementPart.values()) {
            for (Management m : managements) {
                if (p.equals(m.getPart())) {
                    orderedManagementList.add(m);
                }
            }
        }
        return orderedManagementList;
    }
}
