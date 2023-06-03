package ceos.backend.domain.project.service;

import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.helper.ProjectHelper;
import ceos.backend.domain.project.repository.ProjectMapper;
import ceos.backend.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    
    private final ProjectHelper projectHelper;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    @Transactional
    public GetProjectsResponse getProjects() {
        return projectMapper.toGetProjects(projectRepository.findAll());
    }
}
