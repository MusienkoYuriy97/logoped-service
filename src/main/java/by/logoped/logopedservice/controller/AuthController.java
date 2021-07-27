package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.LoginUserRequest;
import by.logoped.logopedservice.dto.RegistrationRequest;
import by.logoped.logopedservice.dto.RegistrationResponse;
import by.logoped.logopedservice.service.AuthService;
import by.logoped.logopedservice.swagger.ApiPostLogin;
import by.logoped.logopedservice.swagger.ApiPostRegistration;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "${api.path}"+"/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "End points for registration and login in account")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration/logoped")
    @ApiPostRegistration
    public ResponseEntity<?> logopedRegistration(@Valid @RequestBody RegistrationRequest registrationRequest){
        RegistrationResponse registrationResponse = authService.saveLogoped(registrationRequest);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/registration/user")
    @ApiPostRegistration
    public ResponseEntity<?> userRegistration(@Valid @RequestBody RegistrationRequest registrationRequest){
        RegistrationResponse registrationResponse = authService.saveUser(registrationRequest);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiPostLogin
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest){
        return new ResponseEntity<>(authService.login(loginUserRequest),
                HttpStatus.OK);
    }

}
