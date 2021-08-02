package by.logoped.logopedservice.swagger;

import by.logoped.logopedservice.dto.response.FormResponse;
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
@Operation(summary = "Get form requests by id")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormResponse.class))),
        @ApiResponse(responseCode = "404", description = "Form not found", content = @Content),
        @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)})
public @interface ApiGetFormById {
}
