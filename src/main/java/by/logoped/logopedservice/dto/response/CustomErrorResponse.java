package by.logoped.logopedservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Error response")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class CustomErrorResponse {
    private String error;

    private int status;

    private LocalDateTime timestamp;
}
