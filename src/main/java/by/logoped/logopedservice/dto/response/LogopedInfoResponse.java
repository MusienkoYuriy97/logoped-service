package by.logoped.logopedservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "Response for get info about logoped")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class LogopedInfoResponse {
    @Schema(example = "Алина")
    private String firstName;

    @Schema(example = "Сасанович")
    private String lastName;

    @Schema(example = "sasanovich@gmail.com")
    private String email;

    @Schema(example = "+375296213287")
    private String phoneNumber;

    @Schema(example = "БГТУ")
    private String education;

    @Schema(example = "Детский сад № 1")
    private String workPlace;

    @Schema(example = "4")
    private int workExperience;

    @Schema(example = "Подготовка к школе")
    private Set<String> category;
}
