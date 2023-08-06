package ceos.backend.domain.project.helper;


import ceos.backend.domain.project.domain.*;
import ceos.backend.domain.project.exception.DataNotFound;
import ceos.backend.domain.project.exception.DuplicateProject;
import ceos.backend.domain.project.exception.ProjectNotFound;
import ceos.backend.domain.project.repository.*;
import ceos.backend.domain.project.vo.ParticipantVo;
import ceos.backend.domain.project.vo.ProjectImageVo;
import ceos.backend.domain.project.vo.ProjectInfoVo;
import ceos.backend.domain.project.vo.ProjectUrlVo;
import ceos.backend.global.common.entity.Part;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectHelper {

    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;
    private final ProjectUrlRepository projectUrlRepository;
    private final ParticipantRepository participantRepository;
    private final ProjectMapper projectMapper;

    public Project findById(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(
                        () -> {
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

    public void updateImages(Project project, List<ProjectImageVo> projectImageVos) {

        for (ProjectImageVo projectImageVo : projectImageVos) {
            ProjectImageCategory category = projectImageVo.getCategory();

            // 데이터베이스에서 이미지를 조회
            ProjectImage image =
                    projectImageRepository
                            .findByProjectAndCategory(project, category)
                            .orElseThrow(
                                    () -> {
                                        throw DataNotFound.EXCEPTION;
                                    });

            image.update(projectImageVo.getImageUrl());
            projectImageRepository.save(image);
        }
    }

    public void updateUrls(Project project, List<ProjectUrlVo> projectUrlVos) {

        for (ProjectUrlVo projectUrlVo : projectUrlVos) {
            ProjectUrlCategory category = projectUrlVo.getCategory();

            // 데이터베이스에서 Url을 조회
            Optional<ProjectUrl> url =
                    projectUrlRepository.findByProjectAndCategory(project, category);

            if (url.isPresent()) {
                ProjectUrl existingUrl = url.get();
                existingUrl.update(projectUrlVo.getLinkUrl());
                projectUrlRepository.save(existingUrl);
            } else {
                ProjectUrl newUrl = ProjectUrl.of(projectUrlVo, project);
                projectUrlRepository.save(newUrl);
            }
        }
    }

    public void updateParticipants(Project project, List<ParticipantVo> participantVos) {

        for (Part part : Part.values()) {
            List<ParticipantVo> filteredList =
                    participantVos.stream()
                            .filter(participant -> participant.getPart().equals(part))
                            .toList();

            participantRepository.deleteAllByProjectAndPart(project, part);
            participantRepository.saveAll(projectMapper.toParticipantList(project, filteredList));
        }
    }
}
