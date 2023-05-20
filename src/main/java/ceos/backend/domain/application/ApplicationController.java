package ceos.backend.domain.application;

import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.response.GetResultResponse;
import ceos.backend.domain.application.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


    @Operation(summary = "서류 합격 여부 확인하기")
    @GetMapping(value = "/document")
    public GetResultResponse getDocumentResult(@RequestParam("uuid") String uuid,
                                               @RequestParam("email") String email) {
        log.info("서류 합격 여부 확인하기");
        return applicationService.getDocumentResult(uuid, email);
    }


    @Operation(summary = "최종 합격 여부 확인하기")
    @GetMapping(value = "/final")
    public GetResultResponse getFinalResult(@RequestParam("uuid") String uuid,
                                            @RequestParam("email") String email) {
        log.info("최종 합격 여부 확인하기");
        return applicationService.getFinalResult(uuid, email);
    }

}
