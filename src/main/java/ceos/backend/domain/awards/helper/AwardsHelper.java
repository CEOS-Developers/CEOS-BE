package ceos.backend.domain.awards.helper;

import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AwardsHelper {

    private final ProjectRepository projectRepository;

    public List<ProjectInfoVo> getProjectInfo(int generation){
        List<Project> projectList = projectRepository.findByGeneration(generation);
        List<ProjectInfoVo> projectsVoList = new ArrayList<>();
        for(Project project: projectList){
            ProjectInfoVo projectInfoVo = ProjectInfoVo.of(project.getGeneration(), project.getName(), project.getDescription());
            projectsVoList.add(projectInfoVo);
        }
        return projectsVoList;
    }
}
