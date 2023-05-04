package ceos.backend.domain.example;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/example")
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping(value = "/responseGet")
    public int reponseGet() {
        return 1;
    }

    @PostMapping(value = "/responsePost")
    public int reponsePost() {
        return 1;
    }

    @PutMapping(value = "/responsePut")
    public int reponsePut() {
        return 1;
    }

    @PatchMapping(value = "/responsePatch")
    public int reponsePatch() {
        return 1;
    }

    @DeleteMapping(value = "/responseDelete")
    public int reponseDelete() {
        return 1;
    }
}
