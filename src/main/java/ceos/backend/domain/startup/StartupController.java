package ceos.backend.domain.startup;


import ceos.backend.domain.startup.dto.request.StartupRequest;
import ceos.backend.domain.startup.dto.response.StartupResponse;
import ceos.backend.domain.startup.dto.response.StartupsResponse;
import ceos.backend.domain.startup.service.StartupService;
import ceos.backend.global.common.dto.AwsS3Url;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/start-ups")
@Tag(name = "Start-Up")
public class StartupController {

    private final StartupService startupService;

    @Operation(summary = "창업 리스트 추가")
    @PostMapping
    public void createStartup(@RequestBody @Valid StartupRequest startupRequest) {
        log.info("창업 리스트 추가");
        startupService.createStartup(startupRequest);
    }

    @Operation(summary = "창업 리스트 보기")
    @GetMapping
    public StartupsResponse getStartups(
            @RequestParam("pageNum") Integer pageNum, @RequestParam("limit") Integer limit) {
        log.info("창업 리스트 보기");
        return startupService.getStartups(pageNum, limit);
    }

    @Operation(summary = "창업 리스트 상세 보기")
    @GetMapping(value = "/{startupId}")
    public StartupResponse getStartup(@PathVariable("startupId") Long startupId) {
        log.info("창업 리스트 상세 보기");
        return startupService.getStartup(startupId);
    }

    @Operation(summary = "창업 리스트 수정")
    @PatchMapping(value = "/{startupId}")
    public StartupResponse updateStartup(
            @PathVariable("startupId") Long startupId,
            @RequestBody @Valid StartupRequest startupRequest) {
        log.info("창업 리스트 수정");
        return startupService.updateStartup(startupId, startupRequest);
    }

    @Operation(summary = "창업 리스트 삭제")
    @DeleteMapping("/{startupId}")
    public void deleteManagement(@PathVariable(name = "startupId") Long startupId) {
        log.info("창업 리스트 삭제");
        startupService.deleteStartup(startupId);
    }

    @Operation(summary = "서비스 이미지 url 생성하기")
    @GetMapping("/image")
    public AwsS3Url getImageUrl() {
        log.info("서비스 이미지 url 생성하기");
        return startupService.getImageUrl();
    }
}
