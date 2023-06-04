package ceos.backend.domain.project.helper;

import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.exception.DuplicateProject;
import ceos.backend.domain.project.exception.ProjectNotFound;
import ceos.backend.domain.project.repository.ProjectImageRepository;
import ceos.backend.domain.project.repository.ProjectRepository;
import ceos.backend.domain.project.vo.ProjectInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectHelper {

    private final ProjectRepository projectRepository;

    public Project findById(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> {
                    throw ProjectNotFound.EXCEPTION;
                });
    }

    public void findDuplicateProject(ProjectInfoVo projectInfoVo) {
        if (projectRepository
                .findByNameAndGeneration(projectInfoVo.getName(), projectInfoVo.getGeneration())
                .isPresent()) {
            throw DuplicateProject.EXCEPTION;
        }
    }
}
