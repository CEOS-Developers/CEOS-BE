package ceos.backend.domain.sponsor;

import ceos.backend.domain.sponsor.service.SponsorService;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.event.Event;
import ceos.backend.infra.s3.AwsS3UrlGenerator;
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
@RequestMapping(value = "/sponsors")
@Tag(name = "Sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    @Operation(summary = "스폰서 이미지 url 생성하기")
    @GetMapping("/image")
    public AwsS3Url getImageUrl() {
        log.info("스폰서 이미지 url 생성하기");
        return sponsorService.getImageUrl();
    }
}
