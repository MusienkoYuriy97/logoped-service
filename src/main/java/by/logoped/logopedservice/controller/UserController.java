package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.request.FormRequest;
import by.logoped.logopedservice.service.UserService;
import by.logoped.logopedservice.swagger.ApiGetAllLogopedInfo;
import by.logoped.logopedservice.swagger.ApiGetLogopedInfo;
import by.logoped.logopedservice.swagger.ApiPostSendForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "${api.path}"+"/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/find/{logopedId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiGetLogopedInfo
    public ResponseEntity<?> get(@PathVariable Long logopedId){
        return new ResponseEntity<>(userService.getById(logopedId),
                HttpStatus.OK);
    }

    @GetMapping("/findall")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiGetAllLogopedInfo
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(userService.getAll(),
                HttpStatus.OK);
    }

    @PostMapping("/form")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiPostSendForm
    public ResponseEntity<?> sendForm(@Valid @RequestBody FormRequest request){
        userService.addFormRequest(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}