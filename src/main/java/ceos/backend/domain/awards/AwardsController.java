package ceos.backend.domain.awards;

import ceos.backend.domain.awards.dto.response.AwardsResponse;
import ceos.backend.domain.awards.dto.request.AwardsRequest;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.domain.awards.dto.response.GenerationAwardsResponse;
import ceos.backend.domain.awards.service.AwardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/awards")
@Tag(name = "Awards")
public class AwardsController {

    private final AwardsService awardsService;

    @Operation(summary = "수상이력 추가하기")
    @PostMapping
    public void createAwards(@RequestBody @Valid AwardsRequest awardsRequest){
        log.info("수상이력 추가하기");
        awardsService.createAwards(awardsRequest);
    }

    @Operation(summary = "수상이력 전체보기")
    @GetMapping
    public AllAwardsResponse getAllAwards(@RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit){
        log.info("수상이력 전체보기");
        return awardsService.getAllAwards(pageNum, limit);
    }

    @Operation(summary = "수상이력 하나보기")
    @GetMapping("/{awardId}")
    public AwardsResponse getAward(@PathVariable(name = "awardId") Long awardID){
        log.info("수상이력 하나보기");
        return awardsService.getAward(awardID);
    }

    @Operation(summary = "수상이력 수정하기")
    @PatchMapping("/{awardId}")
    public AwardsResponse updateAward(@PathVariable(name = "awardId") Long awardID,
                                       @RequestBody AwardsRequest awardsRequest){
        log.info("수상이력 수정하기");
        return awardsService.updateAward(awardID, awardsRequest);
    }

    @Operation(summary = "수상이력 삭제하기")
    @DeleteMapping("/{awardId}")
    public void deleteAwards(@PathVariable(name = "awardId") Long awardID){
        log.info("수상이력 삭제하기");
        awardsService.deleteAward(awardID);
    }
}
