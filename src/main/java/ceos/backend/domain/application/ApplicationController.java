package ceos.backend.domain.application;

import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/applications")
@Tag(name = "Application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "지원하기")
    @PostMapping
    public void createApplication(@RequestBody @Valid CreateApplicationRequest createApplicationRequest) {
        log.info("지원하기");
        applicationService.createApplication(createApplicationRequest);
    }

}
