package ceos.backend.domain.recruitment;

import ceos.backend.domain.recruitment.service.RecruitmentService;
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
@RequestMapping(value = "/recruitments")
@Tag(name = "Recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @Operation(summary = "리크루팅 정보 보기")
    @GetMapping
    public void getSettings() {
        log.info("리크루팅 정보 보기");
        recruitmentService.getSettings();
    }

}
