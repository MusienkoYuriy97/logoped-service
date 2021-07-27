package by.logoped.logopedservice.dto;

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


@Schema(description = "Request for registration")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserRequest {
    @Schema(example = "Yuriy")
    @NotNull @NotBlank
    private String firstName;

    @Schema(example = "Musienko")
    @NotNull @NotBlank
    private String lastName;

    @Schema(example = "97musienko@gmail.com")
    @Pattern(regexp = EMAIL_REGEXP)
    @NotNull @NotBlank
    private String email;

    @Schema(example = "+375298344491")
    @NotNull @NotBlank @Length(min = MIN_PHONE_LENGTH, max = MAX_PHONE_LENGTH, message = PHONE_MSG)
    private String phoneNumber;

    @Schema(example = "11111")
    @NotNull @NotBlank @Length(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = PASSWORD_MSG)
    private String password;
}
