package ceos.backend.domain.sponsor;


import ceos.backend.domain.sponsor.dto.SponsorDto;
import ceos.backend.domain.sponsor.dto.response.GetAllSponsorsResponse;
import ceos.backend.domain.sponsor.service.SponsorService;
import ceos.backend.domain.sponsor.vo.SponsorVo;
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
@RequestMapping(value = "/sponsors")
@Tag(name = "Sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    @Operation(summary = "스폰서 추가하기")
    @PostMapping
    public void createSponsor(@RequestBody @Valid SponsorVo sponsorVo) {
        log.info("스폰서 추가하기");
        sponsorService.createSponsor(sponsorVo);
    }

    @Operation(summary = "스폰서 전체 보기")
    @GetMapping
    public GetAllSponsorsResponse getAllSponsors(
            @RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit) {
        log.info("스폰서 전체 보기");
        return sponsorService.getAllSponsors(pageNum, limit);
    }

    @Operation(summary = "스폰서 하나 보기")
    @GetMapping("/{sponsorId}")
    public SponsorDto getSponsor(@PathVariable(name = "sponsorId") Long sponsorId) {
        log.info("스폰서 하나 보기");
        return sponsorService.getSponsor(sponsorId);
    }

    @Operation(summary = "스폰서 정보 수정")
    @PatchMapping("/{sponsorId}")
    public SponsorDto updateSponsor(
            @PathVariable(name = "sponsorId") Long sponsorId, @RequestBody SponsorVo sponsorVo) {
        log.info("스폰서 정보 수정");
        return sponsorService.updateSponsor(sponsorId, sponsorVo);
    }

    @Operation(summary = "스폰서 삭제")
    @DeleteMapping("/{sponsorId}")
    public void deleteSponsor(@PathVariable(name = "sponsorId") Long sponsorId) {
        log.info("스폰서 삭제");
        sponsorService.deleteSponsor(sponsorId);
    }

    @Operation(summary = "스폰서 이미지 url 생성하기")
    @GetMapping("/image")
    public AwsS3Url getImageUrl() {
        log.info("스폰서 이미지 url 생성하기");
        return sponsorService.getImageUrl();
    }
}
