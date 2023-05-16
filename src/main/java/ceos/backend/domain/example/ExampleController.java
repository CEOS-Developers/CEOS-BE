package ceos.backend.domain.example;


import ceos.backend.domain.example.dto.SlackTest;
import ceos.backend.domain.example.exception.ExampleNotFoundException;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.event.Event;
import ceos.backend.global.common.response.SuccessResponse;
import ceos.backend.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Example Controller", description = "Example Controller 설명입니다.")
@RestController
@RequestMapping(value = "/example")
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping(value = "/responseGet")
    public int responseGet() {
        return 1;
    }

    @PostMapping(value = "/responsePost")
    public int responsePost() {
        return 1;
    }

    @PutMapping(value = "/responsePut")
    public int responsePut() {
        return 1;
    }

    @PatchMapping(value = "/responsePatch")
    public int responsePatch() {
        return 1;
    }

    @DeleteMapping(value = "/responseDelete")
    public int responseDelete() {
        return 1;
    }

    @GetMapping(value = "/exampleError")
    public int exampleError() {
        throw ExampleNotFoundException.EXCEPTION;
    }

    @GetMapping(value = "/globalError")
    public int globalError() {
        throw new RuntimeException();
    }

    @GetMapping(value = "/globalError2")
    public int globalError2(@RequestParam(name = "a") int a) {
        return 1;
    }

    @GetMapping(value = "/slack")
    public int slack(@RequestParam int a, @RequestBody SlackTest slackTest) throws Exception {
        throw new Exception();
    }

    /**
     * Swagger 사용 예시 : GET
     */
    @Operation(summary = "Swagger GET 메서드", description = "Swagger 메서드 설명입니다.", tags = {"Swagger"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/swagger")
    public String swagger() {
        return "Hi, Swagger";
    }

    /**
     * Swagger 사용 예시 : POST
     */
    @Operation(summary = "Swagger POST 메서드", description = "Swagger 메서드 설명입니다.", tags = {"Swagger"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PostMapping(value = "/swagger")
    public String swagger(@Parameter(description = "Swagger") String swagger) {
        return "Hi," + swagger;
    }

    @GetMapping(value = "/mail")
    public int slack(){
        Event.raise(AwsSESMail.from(1));
        return 1;
    }
}
