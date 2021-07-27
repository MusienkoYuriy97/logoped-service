package by.logoped.logopedservice.controller;


import by.logoped.logopedservice.service.LogopedService;
import by.logoped.logopedservice.swagger.ApiGetActivate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "${api.path}"+"/auth")
@RequiredArgsConstructor
@Tag(name = "LogopedController", description = "End points for logoped actions")
public class LogopedController {
    private final LogopedService logopedService;

    @GetMapping("/activate/{jwtActivateKey}")
    @ApiGetActivate
    public ResponseEntity<?> activate(@PathVariable String jwtActivateKey){
        logopedService.activate(jwtActivateKey);
        return new ResponseEntity<>("Successful activate account.",
                HttpStatus.ACCEPTED);
    }
}
