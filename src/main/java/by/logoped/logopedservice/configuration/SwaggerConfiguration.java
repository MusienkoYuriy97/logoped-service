package by.logoped.logopedservice.configuration;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

public class SwaggerConfiguration {
    private static final String API_KEY = "ApiKey";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY,apiKeySecuritySchema()))
                .info(
                        new Info()
                                .title("url-shortener-uaa documentation")
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .email("97musienko@gmail.com")
                                                .name("Yury Musienko")
                                )
                )
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));
    }

//    @Bean
//    public OpenApiCustomiser schemaCustomiser() {
//        ResolvedSchema resolvedSchemaUser = ModelConverters.getInstance()
//                .resolveAsResolvedSchema(new AnnotatedType(?.class));
//        return openApi -> openApi
//                .schema(resolvedSchemaUser.schema.getName(), resolvedSchemaUser.schema);
//    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name("Authorization")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }
}
