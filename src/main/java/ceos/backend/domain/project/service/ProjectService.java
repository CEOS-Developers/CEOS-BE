package ceos.backend.domain.project.service;

import static ceos.backend.domain.project.domain.ProjectImageCategory.THUMBNAIL;

import ceos.backend.domain.project.domain.*;
import ceos.backend.domain.project.dto.request.ProjectRequest;
import ceos.backend.domain.project.dto.response.GetProjectResponse;
import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.helper.ProjectHelper;
import ceos.backend.domain.project.repository.*;
import ceos.backend.domain.project.vo.ParticipantVo;
import ceos.backend.domain.project.vo.ProjectImageVo;
import ceos.backend.domain.project.vo.ProjectUrlVo;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectHelper projectHelper;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;
    private final ProjectUrlRepository projectUrlRepository;
    private final ParticipantRepository participantRepository;
    private final AwsS3UrlHandler awsS3UrlHandler;

    @Transactional(readOnly = true)
    public GetProjectsResponse getProjects(int pageNum, int limit) {
        PageRequest pageRequest = PageRequest.of(pageNum, limit);
        Page<Project> projectList =
                projectRepository.findAllByOrderByGenerationDescIdAsc(pageRequest);
        List<Project> filteredProjects =
                projectList.getContent().stream()
                        .filter(
                                project ->
                                        !project.getProjectImages().stream()
                                                .filter(
                                                        image ->
                                                                image.getCategory()
                                                                        .equals(THUMBNAIL))
                                                .toList()
                                                .isEmpty())
                        .toList();
        PageInfo pageInfo =
                PageInfo.of(
                        pageNum,
                        limit,
                        projectList.getTotalPages(),
                        projectList.getTotalElements());
        return projectMapper.toGetProjects(filteredProjects, pageInfo);
    }

    @Transactional(readOnly = true)
    public GetProjectResponse getProject(Long projectId) {
        return projectMapper.toGetProject(projectHelper.findById(projectId));
    }

    @Transactional
    public void createProject(ProjectRequest projectRequest) {
        // 프로젝트 중복 검사
        projectHelper.findDuplicateProject(projectRequest.getProjectInfoVo());

        // 프로젝트 생성
        final Project project = projectMapper.toEntity(projectRequest);
        projectRepository.save(project);

        // 프로젝트 이미지 저장
        final List<ProjectImageVo> projectImageVos = projectRequest.getProjectImages();
        projectImageRepository.saveAll(projectMapper.toProjectImageList(project, projectImageVos));

        // 프로젝트 Url 저장
        final List<ProjectUrlVo> projectUrlVos = projectRequest.getProjectUrls();
        projectUrlRepository.saveAll(projectMapper.toProjectUrlList(project, projectUrlVos));

        // 프로젝트 팀원 저장
        final List<ParticipantVo> participantVos = projectRequest.getParticipants();
        participantRepository.saveAll(projectMapper.toParticipantList(project, participantVos));
    }

    @Transactional
    public void updateProject(Long projectId, ProjectRequest projectRequest) {
        Project project = projectHelper.findById(projectId);

        // 프로젝트 업데이트
        project.update(projectRequest.getProjectInfoVo());

        projectHelper.updateImages(project, projectRequest.getProjectImages());
        projectHelper.updateUrls(project, projectRequest.getProjectUrls());
        projectHelper.updateParticipants(project, projectRequest.getParticipants());
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectHelper.findById(projectId);

        // 프로젝트 삭제
        projectRepository.delete(project);
    }

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl() {
        return awsS3UrlHandler.handle("projects");
    }
}
