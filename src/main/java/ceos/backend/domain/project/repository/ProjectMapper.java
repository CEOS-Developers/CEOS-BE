package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.Participant;
import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import ceos.backend.domain.project.domain.ProjectUrl;
import ceos.backend.domain.project.dto.request.ProjectRequest;
import ceos.backend.domain.project.dto.response.GetProjectResponse;
import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.vo.ParticipantVo;
import ceos.backend.domain.project.vo.ProjectBriefInfoVo;
import ceos.backend.domain.project.vo.ProjectImageVo;
import ceos.backend.domain.project.vo.ProjectUrlVo;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {

//    public Project toEntity(AddProjectRequest addProjectRequest){
//
//    }

    public GetProjectsResponse toGetProjects(Page<Project> projectList, PageInfo pageInfo) {
        List<ProjectBriefInfoVo> projectBriefInfoVos = projectList.stream()
                .map(ProjectBriefInfoVo::from)
                .toList();
        return GetProjectsResponse.of(projectBriefInfoVos, pageInfo);
    }

    public GetProjectResponse toGetProject(Project project) {
        return GetProjectResponse.from(project);
    }

    public Project toEntity(ProjectRequest projectRequest) {
        return Project.from(projectRequest.getProjectInfoVo());
    }

    public List<ProjectImage> toProjectImageList(Project project, List<ProjectImageVo> projectImageVos) {

        return projectImageVos.stream()
                .map(projectImageVo -> ProjectImage.of(projectImageVo, project))
                .toList();
    }

    public List<ProjectUrl> toProjectUrlList(Project project, List<ProjectUrlVo> projectUrlVos) {

        return projectUrlVos.stream()
                .map(projectUrlVo -> ProjectUrl.of(projectUrlVo, project))
                .toList();
    }

    public List<Participant> toParticipantList(Project project, List<ParticipantVo> participantVos) {

        return participantVos.stream()
                .map(participantVo -> Participant.of(participantVo, project))
                .toList();
    }
}
