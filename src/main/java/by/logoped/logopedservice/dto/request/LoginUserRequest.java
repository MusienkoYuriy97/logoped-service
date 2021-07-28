package by.logoped.logopedservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Login user")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class LoginUserRequest {
    @Schema(example = "musienko@gmail.com")
    @NotNull @NotBlank
    private String email;

    @Schema(example = "1111")
    @NotNull @NotBlank
    private String password;
}