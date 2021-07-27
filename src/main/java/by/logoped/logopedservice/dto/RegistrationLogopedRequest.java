package by.logoped.logopedservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(description = "Request for registration logoped")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationLogopedRequest extends RegistrationUserRequest{
    @Schema(example = "БГТУ")
    @NotNull @NotBlank
    private String education;

    @Schema(example = "Подготовка к школе")
    @NotNull
    private Set<String> services;
}
