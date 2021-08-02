package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.request.FormRequest;
import by.logoped.logopedservice.service.UserService;
import by.logoped.logopedservice.swagger.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "${api.path}"+"/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "End points for user actions")
public class UserController {
    private final UserService userService;

    @GetMapping("/find/{logopedId}")
    @ApiGetLogopedInfo
    public ResponseEntity<?> findLogopedById(@PathVariable Long logopedId){
        return new ResponseEntity<>(userService.findLogopedById(logopedId),
                HttpStatus.OK);
    }

    @GetMapping("/findall")
    @ApiGetAllLogopedInfo
    public ResponseEntity<?> findAllLogoped(){
        return new ResponseEntity<>(userService.findAllLogoped(),
                HttpStatus.OK);
    }

    @PostMapping("/form")
    @ApiPostSendForm
    public ResponseEntity<?> sendFormToLogoped(@Valid @RequestBody FormRequest request){
        userService.addFormRequest(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/file/getall")
    @ApiGetAllFiles
    public ResponseEntity<?> getAllFiles(){
        return new ResponseEntity<>(userService.getAllFiles(),
                HttpStatus.OK);
    }

    @GetMapping("/lesson/getall")
    @ApiGetAllLesson
    public ResponseEntity<?> getAllLesson(){
        return new ResponseEntity<>(userService.getAllLesson(),
                HttpStatus.OK);
    }
}