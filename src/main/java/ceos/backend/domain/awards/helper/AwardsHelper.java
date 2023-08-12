package ceos.backend.domain.awards.helper;


import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.domain.StartDate;
import ceos.backend.domain.awards.dto.response.AwardsResponse;
import ceos.backend.domain.awards.exception.DuplicateGeneration;
import ceos.backend.domain.awards.repository.AwardsRepository;
import ceos.backend.domain.awards.repository.StartDateRepository;
import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AwardsHelper {

    private final ProjectRepository projectRepository;
    private final AwardsRepository awardsRepository;
    private final StartDateRepository startDateRepository;

    public List<ProjectInfoVo> getProjectVo(int generation) {
        List<Project> projectList = projectRepository.findByGeneration(generation);
        List<ProjectInfoVo> projectsVoList = new ArrayList<>();
        for (Project project : projectList) {
            ProjectInfoVo projectInfoVo =
                    ProjectInfoVo.of(project.getName(), project.getDescription());
            projectsVoList.add(projectInfoVo);
        }
        return projectsVoList;
    }

    public List<AwardsResponse> getAwardsDto(int generation) {
        List<Awards> awardsList = awardsRepository.findByGeneration(generation);
        List<AwardsResponse> awardsResponseList = new ArrayList<>();
        for (Awards award : awardsList) {
            AwardsResponse awardsResponse = AwardsResponse.to(award);
            awardsResponseList.add(awardsResponse);
        }
        return awardsResponseList;
    }

    public void validateGeneration(int generation) {
        Optional<StartDate> startDate = startDateRepository.findById(generation);
        if (startDate.isPresent()) {
            throw DuplicateGeneration.EXCEPTION;
        }
    }
}
