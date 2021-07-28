package by.logoped.logopedservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class CustomErrorResponse {
    private String error;

    private int status;

    private LocalDateTime timestamp;
}
