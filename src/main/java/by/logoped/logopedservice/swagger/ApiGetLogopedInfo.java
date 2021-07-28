package by.logoped.logopedservice.swagger;

import by.logoped.logopedservice.dto.response.LogopedInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get logoped info")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LogopedInfoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Fields entered incorrectly", content = @Content),
        @ApiResponse(responseCode = "404", description = "Logoped not found", content = @Content),
        @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)})
public @interface ApiGetLogopedInfo {
}
