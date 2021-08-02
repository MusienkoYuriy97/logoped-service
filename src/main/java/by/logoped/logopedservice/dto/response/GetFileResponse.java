package by.logoped.logopedservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Response for get files")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class GetFileResponse {
    private String fileName;
    private String downloadLink;
    private Long logopedId;
    private Long userId;
}
