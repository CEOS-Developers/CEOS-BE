package ceos.backend.domain.management;

import ceos.backend.domain.management.service.ManagementService;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.event.Event;
import ceos.backend.infra.s3.AwsS3UrlGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mangements")
@Tag(name = "Management")
public class ManagementController {

    private final ManagementService managementService;

    @Operation(summary = "임원진 이미지 url 생성하기")
    @GetMapping("/image")
    public AwsS3Url getImageUrl() {
        log.info("임원진 이미지 url 생성하기");
        return managementService.getImageUrl();
    }
}
