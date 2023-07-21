package ceos.backend.domain.recruitment;


import ceos.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import ceos.backend.domain.recruitment.dto.response.GetRecruitmentResponse;
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

    @Operation(summary = "리크루팅 정보 보기")
    @GetMapping
    public GetRecruitmentResponse getRecruitment() {
        log.info("리크루팅 정보 보기");
        return recruitmentService.getRecruitment();
    }

    @Operation(summary = "리크루팅 정보 수정")
    @PutMapping
    public void updateRecruitment(
            @RequestBody @Valid UpdateRecruitmentRequest updateRecruitmentRequest) {
        log.info("리크루팅 정보 수정");
        recruitmentService.updateRecruitment(updateRecruitmentRequest);
    }
}
