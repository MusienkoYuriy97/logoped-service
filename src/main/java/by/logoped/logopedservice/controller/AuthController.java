package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.request.LoginUserRequest;
import by.logoped.logopedservice.dto.request.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.request.RegistrationUserRequest;
import by.logoped.logopedservice.dto.response.RegistrationResponse;
import by.logoped.logopedservice.service.AuthService;
import by.logoped.logopedservice.swagger.ApiPostLogin;
import by.logoped.logopedservice.swagger.ApiPostLogopedRegistration;
import by.logoped.logopedservice.swagger.ApiPostUserRegistration;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "${api.path}"+"/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "End points for registration and login in account")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration/user")
    @ApiPostUserRegistration
    public ResponseEntity<?> userRegistration(@Valid @RequestBody RegistrationUserRequest request){
        RegistrationResponse response = authService.saveUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registration/logoped")
    @ApiPostLogopedRegistration
    public ResponseEntity<?> logopedRegistration(@Valid @RequestBody RegistrationLogopedRequest request){
        RegistrationResponse response = authService.saveLogoped(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiPostLogin
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request){
        return new ResponseEntity<>(authService.login(request),
                HttpStatus.OK);
    }
}
