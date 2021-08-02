package by.logoped.logopedservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Response for upload file")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class UploadResponse {
    private String fileName;
    private String downloadLink;
}
