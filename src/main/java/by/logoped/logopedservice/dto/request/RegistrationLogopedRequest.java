package by.logoped.logopedservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    private Set<String> categories;

    @Schema(example = "Детский сад № 1")
    @NotNull @NotBlank
    private String workPlace;

    @Schema(example = "5")
    @NotNull @Min(0) @Max(80)
    private int workExperience;

}
