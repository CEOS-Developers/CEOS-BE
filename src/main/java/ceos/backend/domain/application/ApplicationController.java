package ceos.backend.domain.application;

import ceos.backend.domain.application.dto.request.*;
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

    @Operation(summary = "지원서 질문 수정")
    @PutMapping
    public void updateApplicationQuestion(@RequestBody @Valid UpdateApplicationQuestion updateApplicationQuestion) {
        log.info("지원서 질문 수정");
        applicationService.updateApplicationQuestion(updateApplicationQuestion);
    }

    @Operation(summary = "서류 합격 여부 확인하기")
    @GetMapping(value = "/document")
    public GetResultResponse getDocumentResult(@RequestParam("uuid") String uuid,
                                               @RequestParam("email") String email) {
        log.info("서류 합격 여부 확인하기");
        return applicationService.getDocumentResult(uuid, email);
    }

    @Operation(summary = "면접 참여 가능 여부 선택")
    @PatchMapping(value = "/interview")
    public void updateInterviewAttendance(@RequestParam("uuid") String uuid,
                                          @RequestParam("email") String email,
                                          @RequestBody @Valid UpdateAttendanceRequest request) {
        log.info("면접 참여 가능 여부 선택");
        applicationService.updateInterviewAttendance(uuid, email, request);
    }

    @Operation(summary = "최종 합격 여부 확인하기")
    @GetMapping(value = "/final")
    public GetResultResponse getFinalResult(@RequestParam("uuid") String uuid,
                                            @RequestParam("email") String email) {
        log.info("최종 합격 여부 확인하기");
        return applicationService.getFinalResult(uuid, email);
    }

    @Operation(summary = "활동 가능 여부 선택")
    @PatchMapping(value = "/pass")
    public void updateActivityAvailability(@RequestParam("uuid") String uuid,
                                           @RequestParam("email") String email,
                                           @RequestBody @Valid UpdateAttendanceRequest request) {
        log.info("활동 가능 여부 선택");
        applicationService.updateActivityAvailability(uuid, email, request);
    }

    @Operation(summary = "면접 시간 결정하기, 서류 접수 날짜 ~ 서류 결과 발표 전 날")
    @PatchMapping(value = "/{applicationId}/interview")
    public void updateInterviewTime(@PathVariable("applicationId") Long applicationId,
                                    @RequestBody @Valid UpdateInterviewTime updateInterviewTime) {
        log.info("면접 시간 결정하기");
        applicationService.updateInterviewTime(applicationId, updateInterviewTime);
    }

    @Operation(summary = "서류 합격 여부 변경, 서류 접수 날짜 ~ 서류 결과 발표 전 날")
    @PatchMapping(value = "/{applicationId}/document")
    public void updateDocumentPassStatus(@PathVariable("applicationId") Long applicationId,
                                         @RequestBody @Valid UpdatePassStatus updatePassStatus) {
        log.info("서류 합격 여부 변경");
        applicationService.updateDocumentPassStatus(applicationId, updatePassStatus);
    }

    @Operation(summary = "최종 합격 여부 변경, 서류 결과 발표 날짜 ~ 최종 결과 발표 전 날")
    @PatchMapping(value = "/{applicationId}/final")
    public void updateFinalPassStatus(@PathVariable("applicationId") Long applicationId,
                                      @RequestBody @Valid UpdatePassStatus updatePassStatus) {
        log.info("최종 합격 여부 변경");
        applicationService.updateFinalPassStatus(applicationId, updatePassStatus);
    }
}
