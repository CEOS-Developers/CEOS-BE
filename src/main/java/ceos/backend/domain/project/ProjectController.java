package ceos.backend.domain.project;


import ceos.backend.domain.project.dto.request.ProjectRequest;
import ceos.backend.domain.project.dto.response.GetProjectResponse;
import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.service.ProjectService;
import ceos.backend.global.common.dto.AwsS3Url;
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
    @GetMapping
    public GetProjectsResponse getProjects(
            @RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit) {
        log.info("프로젝트 목록 보기");
        return projectService.getProjects(pageNum, limit);
    }

    @Operation(summary = "프로젝트 하나 보기")
    @GetMapping("/{projectId}")
    public GetProjectResponse getProject(@PathVariable("projectId") Long projectId) {
        log.info("프로젝트 하나 보기");
        return projectService.getProject(projectId);
    }

    @Operation(summary = "프로젝트 생성하기")
    @PostMapping
    public void createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        log.info("프로젝트 생성하기");
        projectService.createProject(projectRequest);
    }

    @Operation(summary = "프로젝트 수정하기")
    @PatchMapping("/{projectId}")
    public void updateProject(
            @PathVariable("projectId") Long projectId, @RequestBody @Valid ProjectRequest projectRequest) {
        log.info("프로젝트 수정하기");
        projectService.updateProject(projectId, projectRequest);
    }

    @Operation(summary = "프로젝트 삭제하기")
    @DeleteMapping("/{projectId}")
    public void deleteActivity(@PathVariable Long projectId) {
        log.info("프로젝트 삭제하기");
        projectService.deleteProject(projectId);
    }

    @Operation(summary = "프로젝트 이미지 url 생성하기")
    @GetMapping("/image")
    public AwsS3Url getImageUrl() {
        log.info("프로젝트 이미지 url 생성하기");
        return projectService.getImageUrl();
    }
}
