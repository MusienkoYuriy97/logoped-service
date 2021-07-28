package by.logoped.logopedservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static by.logoped.logopedservice.dto.ValidationConstant.*;


@Schema(description = "Registration user")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class RegistrationUserRequest {
    @Schema(example = "Юрий")
    @NotNull @NotBlank
    private String firstName;

    @Schema(example = "Мусиенко")
    @NotNull @NotBlank
    private String lastName;

    @Schema(example = "musienko@gmail.com")
    @Pattern(regexp = EMAIL_REGEXP)
    @NotNull @NotBlank
    private String email;

    @Schema(example = "+375298344490")
    @NotNull @NotBlank @Length(min = MIN_PHONE_LENGTH, max = MAX_PHONE_LENGTH, message = PHONE_MSG)
    private String phoneNumber;

    @Schema(example = "1111")
    @NotNull @NotBlank @Length(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = PASSWORD_MSG)
    private String password;
}
