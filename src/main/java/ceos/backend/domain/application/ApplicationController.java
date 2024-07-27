package ceos.backend.domain.application;


import ceos.backend.domain.application.domain.Pass;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateApplicationQuestion;
import ceos.backend.domain.application.dto.request.UpdateAttendanceRequest;
import ceos.backend.domain.application.dto.request.UpdateInterviewTime;
import ceos.backend.domain.application.dto.request.UpdatePassStatus;
import ceos.backend.domain.application.dto.response.GetApplication;
import ceos.backend.domain.application.dto.response.GetApplicationQuestion;
import ceos.backend.domain.application.dto.response.GetApplications;
import ceos.backend.domain.application.dto.response.GetCreationTime;
import ceos.backend.domain.application.dto.response.GetInterviewTime;
import ceos.backend.domain.application.dto.response.GetResultResponse;
import ceos.backend.domain.application.service.ApplicationExcelService;
import ceos.backend.domain.application.service.ApplicationService;
import ceos.backend.global.common.entity.Part;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/applications")
@Tag(name = "Application")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ApplicationExcelService applicationExcelService;

    @Operation(summary = "지원자 목록 보기")
    @GetMapping
    public GetApplications getApplications(
            @RequestParam(value = "part", required = false) Part part,
            @RequestParam(value = "docPass", required = false) Pass docPass,
            @RequestParam(value = "finalPass", required = false) Pass finalPass,
            @RequestParam(value = "applicantName", required = false) String applicantName,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("limit") int limit) {
        log.info("지원자 목록 보기");
        return applicationService.getApplications(
                part, docPass, finalPass, applicantName, pageNum, limit);
    }

    @Operation(summary = "지원하기", description = "startDateDoc ~ endDateDoc 전날")
    @PostMapping
    public void createApplication(
            @RequestBody @Valid CreateApplicationRequest createApplicationRequest) {
        log.info("지원하기");
        applicationService.createApplication(createApplicationRequest);
    }

    @Operation(summary = "지원서 질문 가져오기")
    @GetMapping(value = "/question")
    public GetApplicationQuestion getApplicationQuestion() {
        log.info("지원서 질문 가져오기");
        return applicationService.getApplicationQuestion();
    }

    @Operation(summary = "지원서 질문 수정", description = "~ startDateDoc 전날")
    @PutMapping(value = "/question")
    public void updateApplicationQuestion(
            @RequestBody @Valid UpdateApplicationQuestion updateApplicationQuestion) {
        log.info("지원서 질문 수정");
        applicationService.updateApplicationQuestion(updateApplicationQuestion);
    }

    @Operation(summary = "서류 합격 여부 확인하기", description = "resultDateDoc ~ resultDateFinal 전날")
    @GetMapping(value = "/document")
    public GetResultResponse getDocumentResult(
            @RequestParam("uuid") String uuid, @RequestParam("email") String email) {
        log.info("서류 합격 여부 확인하기");
        return applicationService.getDocumentResult(uuid, email);
    }

    @Operation(summary = "면접 참여 가능 여부 선택", description = "resultDateDoc ~ resultDateFinal 전날")
    @PatchMapping(value = "/interview")
    public void updateInterviewAttendance(
            @RequestParam("uuid") String uuid,
            @RequestParam("email") String email,
            @RequestBody UpdateAttendanceRequest request) {
        log.info("면접 참여 가능 여부 선택");
        applicationService.updateInterviewAttendance(uuid, email, request);
    }

    @Operation(summary = "최종 합격 여부 확인하기", description = "resultDateFinal ~ resultDateFinal 4일 후")
    @GetMapping(value = "/final")
    public GetResultResponse getFinalResult(
            @RequestParam("uuid") String uuid, @RequestParam("email") String email) {
        log.info("최종 합격 여부 확인하기");
        return applicationService.getFinalResult(uuid, email);
    }

    @Operation(summary = "활동 가능 여부 선택", description = "resultDateFinal ~ resultDateFinal 4일 후")
    @PatchMapping(value = "/pass")
    public void updateParticipationAvailability(
            @RequestParam("uuid") String uuid,
            @RequestParam("email") String email,
            @RequestBody UpdateAttendanceRequest request) {
        log.info("활동 가능 여부 선택");
        applicationService.updateParticipationAvailability(uuid, email, request);
    }

    @Operation(summary = "지원자 자기소개서 보기")
    @GetMapping(value = "/{applicationId}")
    public GetApplication getApplication(@PathVariable("applicationId") Long applicationId) {
        log.info("지원자 자기소개서 보기");
        return applicationService.getApplication(applicationId);
    }

    @Operation(summary = "면접 시간 정보 가져오기")
    @GetMapping(value = "/{applicationId}/interview")
    public GetInterviewTime getInterviewTime(@PathVariable("applicationId") Long applicationId) {
        log.info("면접 시간 정보 가져오기");
        return applicationService.getInterviewTime(applicationId);
    }

    @Operation(summary = "면접 시간 결정하기", description = "startDateDoc ~ resultDateDoc 전날")
    @PatchMapping(value = "/{applicationId}/interview")
    public void updateInterviewTime(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody @Valid UpdateInterviewTime updateInterviewTime) {
        log.info("면접 시간 결정하기");
        applicationService.updateInterviewTime(applicationId, updateInterviewTime);
    }

    @Operation(summary = "서류 합격 여부 변경", description = "startDateDoc ~ resultDateDoc 전날")
    @PatchMapping(value = "/{applicationId}/document")
    public void updateDocumentPassStatus(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody @Valid UpdatePassStatus updatePassStatus) {
        log.info("서류 합격 여부 변경");
        applicationService.updateDocumentPassStatus(applicationId, updatePassStatus);
    }

    @Operation(summary = "최종 합격 여부 변경", description = "resultDateDoc ~ ResultDateFinal 전날")
    @PatchMapping(value = "/{applicationId}/final")
    public void updateFinalPassStatus(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody @Valid UpdatePassStatus updatePassStatus) {
        log.info("최종 합격 여부 변경");
        applicationService.updateFinalPassStatus(applicationId, updatePassStatus);
    }

    @Operation(summary = "지원서 엑셀 파일 생성")
    @GetMapping(value = "/file/create")
    public GetCreationTime createApplicationExcel() {
        log.info("지원서 엑셀 파일 생성");
        return applicationExcelService.createApplicationExcel();
    }

    @Operation(summary = "지원서 엑셀 다운로드")
    @GetMapping(value = "/file/download")
    public ResponseEntity<FileSystemResource> getApplicationExcel() {
        log.info("지원서 엑셀 다운로드");
        Path path = applicationExcelService.getApplicationExcel();

        FileSystemResource resource = new FileSystemResource(path.toFile());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + path.getFileName().toString());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(path.toFile().length())
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    @Operation(summary = "지원서 엑셀 파일 생성 시각 확인")
    @GetMapping(value = "/file/creationtime")
    public GetCreationTime getApplicationExcelCreationTime() {
        log.info("지원서 엑셀 파일 생성 시각 확인");
        return applicationExcelService.getApplicationExcelCreationTime();
    }

    @Operation(summary = "지원서 전체 삭제")
    @DeleteMapping(value = "/delete")
    public void deleteAllApplications() {
        log.info("지원서 전체 삭제");
        applicationService.deleteAllApplications();
    }
}
