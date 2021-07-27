package by.logoped.logopedservice.dto;

import by.logoped.logopedservice.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "Response for registration")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Schema(example = "Подготовка к школе")
    private Set<String> category;
}
