package ceos.backend.domain.retrospect;


import ceos.backend.domain.retrospect.dto.request.CreateRetrospectRequest;
import ceos.backend.domain.retrospect.dto.response.GetRetrospectResponse;
import ceos.backend.domain.retrospect.dto.response.GetRetrospectsResponse;
import ceos.backend.domain.retrospect.service.RetrospectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/retrospect")
@Tag(name = "Retrospect")
public class RetrospectController {
    private final RetrospectService retrospectService;

    @Operation(summary = "회고록 목록 보기")
    @GetMapping
    public GetRetrospectsResponse getRetrospects(
            @RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit) {
        log.info("회고록 목록 보기");

        return retrospectService.getRetrospects(pageNum, limit);
    }

    @Operation(summary = "회고록 상세 보기")
    @GetMapping("/{id}")
    public GetRetrospectResponse getRetrospect(@PathVariable("id") Long id) {
        log.info("회고록 상세 보기");

        return retrospectService.getRetrospect(id);
    }

    @Operation(summary = "회고록 작성")
    @PostMapping
    public void createRetrospect(
            @RequestBody @Valid CreateRetrospectRequest createRetrospectRequest) {
        log.info("회고록 작성");

        retrospectService.createRetrospect(createRetrospectRequest);
    }

    @Operation(summary = "회고록 수정")
    @PutMapping("/{id}")
    public GetRetrospectResponse updateRetrospect(
            @PathVariable("id") Long id,
            @RequestBody @Valid CreateRetrospectRequest createRetrospectRequest) {
        log.info("회고록 수정");

        return retrospectService.updateRetrospect(id, createRetrospectRequest);
    }

    @Operation(summary = "회고록 삭제")
    @DeleteMapping("/{id}")
    public void deleteRetrospect(@PathVariable("id") Long id) {
        log.info("회고록 삭제");

        retrospectService.deleteRetrospect(id);
    }
}
