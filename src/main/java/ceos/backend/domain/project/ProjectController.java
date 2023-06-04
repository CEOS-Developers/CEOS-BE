package ceos.backend.domain.project;

import ceos.backend.domain.project.dto.request.CreateProjectRequest;
import ceos.backend.domain.project.dto.response.GetProjectResponse;
import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/projects")
@Tag(name = "Project")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "프로젝트 목록 보기")
    @GetMapping("/")
    public GetProjectsResponse getProjects(@RequestParam("pageNum") int pageNum,
                                           @RequestParam("limit") int limit) {
        log.info("프로젝트 목록 보기");
        return projectService.getProjects(pageNum, limit);
    }

    @Operation(summary = "프로젝트 생성하기")
    @PostMapping("/")
    public void createProject(@RequestBody @Valid CreateProjectRequest createProjectRequest) {
        log.info("프로젝트 생성하기");
        projectService.createProject(createProjectRequest);
    }

    @Operation(summary = "프로젝트 하나 보기")
    @GetMapping("/{projectId}")
    public GetProjectResponse getProject(
            @PathVariable("projectId") Long projectId
    ) {
        log.info("프로젝트 하나 보기");
        return projectService.getProject(projectId);
    }

//    @Operation(summary = "프로젝트 수정하기")
//    @PatchMapping("/")
//    public void updateProject(@RequestBody @Valid UpdateProjectRequest updateProjectRequest) {
//        log.info("프로젝트 수정하기");
//        projectService.updateProject(updateProjectRequest);
//    }

}
