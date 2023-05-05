package ceos.backend.domain.example;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
