package by.logoped.logopedservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Create form request")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormRequest {
    @Schema(example = "1")
    @NotNull
    private Long logopedId;

    @Schema(example = "+375296213287")
    @NotNull @NotBlank
    private String phoneNumber;

    @Schema(example = "Не выговаривает звук ль")
    @NotNull @NotBlank
    private String description;
}
