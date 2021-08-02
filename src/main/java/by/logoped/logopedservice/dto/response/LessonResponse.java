package by.logoped.logopedservice.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Lesson response")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class LessonResponse {
    private Long logopedId;
    private Long userId;
    private String zoomLink;
    private String address;
    private LocalDateTime startTime;
}
