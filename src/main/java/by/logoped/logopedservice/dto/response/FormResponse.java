package by.logoped.logopedservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response for form request")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class FormResponse {
    @Schema(example = "+375298344491")
    private String phoneNumber;

    @Schema(example = "Не говорит звук Р")
    private String description;
}
