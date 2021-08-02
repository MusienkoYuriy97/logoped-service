package by.logoped.logopedservice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "File upload for logoped")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CREATED", content = @Content),
        @ApiResponse(responseCode = "400", description = "System error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Logoped not found", content = @Content),
        @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)})
public @interface ApiPostFileUpload {
}
