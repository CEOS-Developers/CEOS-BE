package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.vo.ProjectBriefInfoVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {

//    public Project toEntity(AddProjectRequest addProjectRequest){
//
//    }

    public GetProjectsResponse toGetProjects(List<Project> projectList) {

        List<ProjectBriefInfoVo> projectBriefInfoVos = projectList.stream()
                .map(ProjectBriefInfoVo::from)
                .toList();
        return GetProjectsResponse.from(projectBriefInfoVos);
    }
}
