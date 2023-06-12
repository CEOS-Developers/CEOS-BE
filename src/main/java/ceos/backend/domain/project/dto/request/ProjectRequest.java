package ceos.backend.domain.project.dto.request;

import ceos.backend.domain.project.vo.ParticipantVo;
import ceos.backend.domain.project.vo.ProjectImageVo;
import ceos.backend.domain.project.vo.ProjectInfoVo;
import ceos.backend.domain.project.vo.ProjectUrlVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectRequest {

    @JsonUnwrapped
    private ProjectInfoVo projectInfoVo;

    @Valid
    private List<ProjectUrlVo> projectUrls;

    @Valid
    private List<ProjectImageVo> projectImages;

    @Valid
    private List<ParticipantVo> participants;
}
