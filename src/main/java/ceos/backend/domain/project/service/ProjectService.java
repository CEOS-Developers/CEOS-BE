package ceos.backend.domain.project.service;

import ceos.backend.domain.project.domain.*;
import ceos.backend.domain.project.dto.request.CreateProjectRequest;
import ceos.backend.domain.project.dto.response.GetProjectResponse;
import ceos.backend.domain.project.dto.response.GetProjectsResponse;
import ceos.backend.domain.project.helper.ProjectHelper;
import ceos.backend.domain.project.repository.*;
import ceos.backend.domain.project.vo.ParticipantVo;
import ceos.backend.domain.project.vo.ProjectImageVo;
import ceos.backend.domain.project.vo.ProjectUrlVo;
import ceos.backend.global.common.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public GetProjectsResponse getProjects(int pageNum, int limit) {
        PageRequest pageRequest = PageRequest.of(pageNum, limit);
        Page<Project> projectList = projectRepository.findAll(pageRequest);
        PageInfo pageInfo = PageInfo.of(pageNum, limit, projectList.getTotalPages(), projectList.getTotalElements());
        return projectMapper.toGetProjects(projectList, pageInfo);
    }

    @Transactional
    public void createProject(CreateProjectRequest createProjectRequest) {
        //프로젝트 중복 검사
        projectHelper.findDuplicateProject(createProjectRequest.getProjectInfoVo());

        //프로젝트 생성
        final Project project = projectMapper.toEntity(createProjectRequest);
        projectRepository.save(project);

        //프로젝트 이미지 저장
        final List<ProjectImageVo> projectImageVos = createProjectRequest.getProjectImages();
        projectImageRepository.saveAll(projectMapper.toProjectImageList(project, projectImageVos));

        //프로젝트 Url 저장
        final List<ProjectUrlVo> projectUrlVos = createProjectRequest.getProjectUrls();
        projectUrlRepository.saveAll(projectMapper.toProjectUrlList(project, projectUrlVos));

        //프로젝트 팀원 저장
        final List<ParticipantVo> participantVos = createProjectRequest.getParticipants();
        participantRepository.saveAll(projectMapper.toParticipantList(project, participantVos));
    }

    @Transactional(readOnly = true)
    public GetProjectResponse getProject(Long projectId) {
        return projectMapper.toGetProject(projectHelper.findById(projectId));
    }

    @Transactional
    public void updateProject(Long projectId) {

    }
}
