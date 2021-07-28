package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.service.AdminService;
import by.logoped.logopedservice.swagger.ApiPatchBanUser;
import by.logoped.logopedservice.swagger.ApiPatchUnBanUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "${api.path}"+"/admin")
@RequiredArgsConstructor
@Tag(name = "AdminController", description = "End points for admin actions")
public class AdminController {
    private final AdminService adminService;

    @PatchMapping("/ban/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiPatchBanUser
    public ResponseEntity<?> userBan(@Valid @NotNull @PathVariable Long userId){
        adminService.ban(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/unban/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiPatchUnBanUser
    public ResponseEntity<?> userUnBan(@PathVariable Long userId){
        adminService.unBan(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
