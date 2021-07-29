package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.service.LogopedService;
import by.logoped.logopedservice.swagger.ApiGetActivate;
import by.logoped.logopedservice.swagger.ApiGetAllForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.path}"+"/logoped")
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

    @GetMapping("/form/getall")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    @ApiGetAllForm
    public ResponseEntity<?> getAllForm(){
        return new ResponseEntity<>(logopedService.getAllForm(),
                HttpStatus.OK);
    }

    @GetMapping("/form/get/{userId}")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    @ApiGetAllForm
    public ResponseEntity<?> getForm(@PathVariable String userId){
        return new ResponseEntity<>(
                HttpStatus.OK);
    }
}