package ceos.backend.domain.awards;

import ceos.backend.domain.awards.dto.AwardsDto;
import ceos.backend.domain.awards.dto.request.CreateAwardsRequest;
import ceos.backend.domain.awards.dto.response.GetAllAwardsResponse;
import ceos.backend.domain.awards.service.AwardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/awards")
@Tag(name = "Awards")
public class AwardsController {

    private final AwardsService awardsService;

    @Operation(summary = "수상이력 추가하기")
    @PostMapping
    public void createAwards(@RequestBody @Valid CreateAwardsRequest createAwardsRequest){
        log.info("수상이력 추가하기");
        awardsService.createAwards(createAwardsRequest);
    }

    @Operation(summary = "수상이력 전체보기")
    @GetMapping
    public GetAllAwardsResponse getAllAwards(@RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit){
        log.info("수상이력 전체보기");
        return awardsService.getAllAwards(pageNum, limit);
    }

    @Operation(summary = "수상이력 하나보기")
    @GetMapping("/{awardId}")
    public AwardsDto getAwards(@PathVariable(name = "awardId") Long awardID){
        log.info("수상이력 하나보기");
        return awardsService.getAward(awardID);
    }

    @Operation(summary = "수상이력 수정하기")
    @PatchMapping("/{awardId}")
    public void updateAwards(@PathVariable(name = "awardId") Long awardID){
        log.info("수상이력 수정하기");
    }

    @Operation(summary = "수상이력 삭제하기")
    @DeleteMapping("/{awardId}")
    public void deleteAwards(@PathVariable(name = "awardId") Long awardID){
        log.info("수상이력 삭제하기");
    }
}
