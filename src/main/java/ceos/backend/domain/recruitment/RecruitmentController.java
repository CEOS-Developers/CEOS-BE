package ceos.backend.domain.recruitment;


import ceos.backend.domain.recruitment.dto.RecruitmentDTO;
import ceos.backend.domain.recruitment.dto.UserRecruitmentDTO;
import ceos.backend.domain.recruitment.service.RecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recruitments")
@Tag(name = "Recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @Operation(summary = "모든 리크루팅 정보 보기 (오픈채팅 포함)")
    @GetMapping("/all")
    public RecruitmentDTO getRecruitment() {
        log.info("리크루팅 정보 보기");
        return recruitmentService.getRecruitment();
    }

    @Operation(summary = "리크루팅 정보 보기")
    @GetMapping
    public UserRecruitmentDTO getUserRecruitment() {
        log.info("리크루팅 정보 보기");
        return recruitmentService.getUserRecruitment();
    }

    @Operation(summary = "리크루팅 정보 수정")
    @PutMapping
    public void updateRecruitment(@RequestBody @Valid RecruitmentDTO recruitmentDTO) {
        log.info("리크루팅 정보 수정");
        recruitmentService.updateRecruitment(recruitmentDTO);
    }
}
