package by.logoped.logopedservice.dto;

import by.logoped.logopedservice.entity.Role;
import by.logoped.logopedservice.util.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;


@Schema(description = "Response for registration")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    @Schema(example = "Yury")
    private String firstName;

    @Schema(example = "Musienko")
    private String lastName;

    @Schema(example = "97musienko@gmail.com")
    private String email;

    @Schema(example = "+375298344491")
    private String phoneNumber;

    @Schema(example = "ACTIVE")
    private UserStatus userStatus;

    @Schema(example = "ROLE_USER")
    private List<String> roles;
}
