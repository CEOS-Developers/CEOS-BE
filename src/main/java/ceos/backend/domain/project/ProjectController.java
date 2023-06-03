package ceos.backend.domain.project;

import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/projects")
@Tag(name = "Project")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "프로젝트 목록 보기")
    @GetMapping("/")
    public GetProjectsResponse getProjects() {
        log.info("프로젝트 목록 보기");
        return projectService.getProjects();
    }
}
