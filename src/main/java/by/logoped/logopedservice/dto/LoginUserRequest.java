package by.logoped.logopedservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Object for login user in system")
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class LoginUserRequest {
    @Schema(example = "97musienko@gmail.com")
    @NotNull @NotBlank
    private String email;

    @Schema(example = "12345")
    @NotNull @NotBlank
    private String password;
}