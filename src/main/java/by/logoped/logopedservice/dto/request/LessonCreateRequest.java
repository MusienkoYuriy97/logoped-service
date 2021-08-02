package by.logoped.logopedservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Create lesson request")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonCreateRequest {
    @NotNull @Min(1)
    private Long userId;
    private String zoomLink;
    private String address;
    @NotNull
    private LocalDateTime startTime;
}