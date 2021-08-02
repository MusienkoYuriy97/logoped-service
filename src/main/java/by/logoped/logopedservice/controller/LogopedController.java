package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.request.LessonCreateRequest;
import by.logoped.logopedservice.service.LogopedService;
import by.logoped.logopedservice.swagger.ApiGetActivate;
import by.logoped.logopedservice.swagger.ApiGetAllForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/form/get/{formId}")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> getForm(@PathVariable Long formId){
        return new ResponseEntity<>(logopedService.getForm(formId),
                HttpStatus.OK);
    }

    @DeleteMapping("/form/delete/{formId}")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> deleteForm(@PathVariable Long formId){
        logopedService.deleteForm(formId);
        return new ResponseEntity<>(
                HttpStatus.OK);
    }

    @PostMapping("/file/upload")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile){
        logopedService.uploadFile(multipartFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/file/upload/{userId}")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> sendFileToClient(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long userId){
        logopedService.uploadFile(multipartFile, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/file/getall")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> getAllFiles(){
        return new ResponseEntity<>(logopedService.getAllFiles(),
                HttpStatus.OK);
    }

    @PostMapping("/lesson/create")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> createLesson(@RequestBody LessonCreateRequest request){
        logopedService.createLesson(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/lesson/getall")
    @PreAuthorize("hasRole('ROLE_LOGOPED')")
    public ResponseEntity<?> getAllLesson(){

        return new ResponseEntity<>(logopedService.getAllLesson(),
                HttpStatus.OK);
    }
}